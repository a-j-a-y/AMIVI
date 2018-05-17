package consumer;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Consumer {

    private int consumerID;
    private final Timer timer = new Timer();
//    private String consumerName;

    public Consumer(int ID) {
        this.consumerID = ID;
    }
    public int getID() {
        return consumerID;
    }

    public void subscribe(String topicName) {
        // subscribe to a topic from the broker
    }

    public List<ConsumerRecord> poll() {
        long defaultMS = 100;
        List<ConsumerRecord> polledRecords=null;

//        polledRecords = poll(defaultMS);

        return polledRecords;
    }

    public void poll(long intervalTime) {

        final List<SerializedRecord> polledRecords = null;

        // schedule a task to run at an interval of time
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // polling code goes here for retrieving data from broker
                // use polledRecords here to store the retrieved records


                Deserializer deserializer = new StringDeserializer();
                List<DeserializedKeyValue> deserializedKeyValuesBatch = new LinkedList<DeserializedKeyValue>();

                for(SerializedRecord record : polledRecords) {
                    String key = deserializer.deserialize(record.getKey());
                    String value = deserializer.deserialize(record.getValue());
                    DeserializedKeyValue deserializedKeyValue = new DeserializedKeyValue(record.getTopicName(), key, value);

                    deserializedKeyValuesBatch.add(deserializedKeyValue);
                }

//                toTheUser(deserializedKeyValuesBatch);
            }
        }, 0, intervalTime);

//        return dese;
    }
}