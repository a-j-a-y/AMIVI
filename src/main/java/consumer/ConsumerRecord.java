package consumer;

import java.util.List;

public class ConsumerRecord {

    private String topicName;
    private byte[] key;
    private byte[] value;

    public ConsumerRecord(List<SerializedRecord> records){

       for(SerializedRecord record: records ) {
            this.topicName=topicName;
            this.key=key;
            this.value=value;
        }
    }
}
