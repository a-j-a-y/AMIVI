package broker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class OffsetGenerator {

    private Map<String, Long> topicOffsetMap = new HashMap<>();  //offset map containing topic name and last offset generated for a topic by any producer.

    List<OffsetRecord> generate(List<SerializedRecord> serializedRecords) {

        String currentTopicName;
        long currentOffsetValue;    // stores the first available offset for a topic

        List<OffsetRecord> offsetRecords = new LinkedList<>();

        for (SerializedRecord record : serializedRecords) {

            currentTopicName = record.getTopicName();

            if (!topicOffsetMap.containsKey(currentTopicName)) {
                topicOffsetMap.put(currentTopicName, 0L);
            }

            currentOffsetValue = topicOffsetMap.get(currentTopicName);

            offsetRecords.add(new OffsetRecord(record, currentOffsetValue));

            topicOffsetMap.put(currentTopicName, currentOffsetValue + 1);
        }

        return offsetRecords;
    }
}
