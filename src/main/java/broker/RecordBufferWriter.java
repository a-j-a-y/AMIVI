package broker;

import java.util.*;
import java.util.concurrent.*;

class RecordBufferWriter {

    private Map<String, Topic> topics = new HashMap<>();    // HashMap containing topicName as key & Topic Object as corresponding value.

    private ConcurrentHashMap<String, CopyOnWriteArrayList<OffsetRecord>> topicWiseRecordBuffer = new ConcurrentHashMap<>();

    private final Timer timer = new Timer();

//    RecordBufferWriter() {
//        long defaultWriteInterval = 100;
////        writer(defaultWriteInterval);
//    }

    void addRecords(List<OffsetRecord> offsetRecords) {
        String currentTopicName;

        for (OffsetRecord record : offsetRecords) {

            currentTopicName = record.getRecord().getTopicName();

            CopyOnWriteArrayList<OffsetRecord> offsetRecordCAL;// = new LinkedBlockingQueue<>();

            offsetRecordCAL = topicWiseRecordBuffer.get(currentTopicName);

            offsetRecordCAL.add(record);

            topicWiseRecordBuffer.put(currentTopicName, offsetRecordCAL);

            if (!topics.containsKey(currentTopicName)) {
                try {
                    //this creates a new topic with a new active-segment
                    Topic topic = new Topic(currentTopicName, record);
                    // put the new topic in the map of existing one.
                    topics.put(currentTopicName, topic);
                    // initialize the offset map;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        writer();
        //////!!!!!!!!!!!!!!!!!CALL WRITER HERE AND REMOVE TIMER

    }

    private void writer() {

//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
                ExecutorService executorService = Executors.newCachedThreadPool();

                for (Map.Entry<String, CopyOnWriteArrayList<OffsetRecord>> entry : topicWiseRecordBuffer.entrySet()) {
                    final String currentKey = entry.getKey();
                    final CopyOnWriteArrayList<OffsetRecord> currentValue = entry.getValue();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            executeWrite(currentKey, currentValue);
                        }
                    });
                    topicWiseRecordBuffer.remove(currentKey, currentValue);
                }

//            }
//        };

//        timer.scheduleAtFixedRate(task, 1000, defaultWriteInterval);

    }

    private void executeWrite(String topicName, List<OffsetRecord> offsetRecords) {
        long flushPosition;

        for (OffsetRecord offsetRecord : offsetRecords) {
            flushPosition = topics.get(topicName).getActiveSegment().flushData(offsetRecord);

            if (flushPosition == -1) {
                topics.get(topicName).getSegments().push(topics.get(topicName).createSegment());
                flushPosition = topics.get(topicName).getActiveSegment().flushData(offsetRecord);
            }
        }
    }

    Map<String, Topic> getTopics() {
        return topics;
    }
}
