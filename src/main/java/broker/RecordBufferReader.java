package broker;

import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordBufferReader {

    private Map<Integer, HashMap<String, Long>> consumerOffsetMap = new HashMap<>();

//    RecordBufferReader(Map<String, Topic> topics) {
//        this.topics = topics;
//    }

    List<SerializedRecord> readRecordBuffer(Map<String, Topic> topics, String topicName, int clientId, int batchSize) {

        List<SerializedRecord> serializedRecords = null;

        HashMap<String, Long> topicWiseOffset;

        long currentOffset;

        long readPosition;

        Topic currentTopic = topics.get(topicName);

        if (!consumerOffsetMap.containsKey(clientId)) {

            topicWiseOffset = new HashMap<>();

            topicWiseOffset.put(topicName, 0L);

            consumerOffsetMap.put(clientId, topicWiseOffset);
        }

        if (!consumerOffsetMap.get(clientId).containsKey(topicName)) {
            consumerOffsetMap.get(clientId).put(topicName, 0L);
        }

        // retrieve the offset from which to send records to client/consumer
        currentOffset = consumerOffsetMap.get(clientId).get(topicName);

        Deque<Segment> segments = currentTopic.getSegments();

        for (Segment segment : segments) {
            try {
                serializedRecords = segment.readRecord(topicName, currentOffset, batchSize);

                if (serializedRecords.size() >= batchSize) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        currentOffset += batchSize;

        // update the offset in the map
        consumerOffsetMap.get(clientId).put(topicName, currentOffset);

        return serializedRecords;
    }

}
