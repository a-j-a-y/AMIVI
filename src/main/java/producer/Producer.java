package producer;
import java.util.List;

import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;


public class Producer {


    Serializer keySerializer;
    Serializer valueSerializer;
    int capacity = 100;

    List<SerializedKeyValue> topicBuffer = new CopyOnWriteArrayList<SerializedKeyValue>();
    List<SerializedKeyValue> topicBufferTransient;
    BlockingQueue<List<SerializedKeyValue>> blockingQueue = new ArrayBlockingQueue<List<SerializedKeyValue>>(capacity);

    public Producer(Serializer keySerializer, Serializer valueSerializer) {

        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;

//        sender();///
    }


    /**
     * send producer record exposed to client
     */
    void send(ProducerRecord producerRecord) {

        SerializedKeyValue serializedKeyValue = new SerializedKeyValue(keySerializer.serialize(producerRecord.getKey()), valueSerializer.serialize(producerRecord.getValue()), producerRecord.getTopicName() );
        addToBuffer(serializedKeyValue);

    }




    /**
     * adding serialized key and seralized value to buffer(list)
     *
     * @param serializedKeyValue
     */
    void addToBuffer(SerializedKeyValue serializedKeyValue) {

        topicBuffer.add(serializedKeyValue);

        // task for the new thread
        Runnable sendTopicBufferBQ = new Runnable() {
            public void run() {
                try {
                    sendToBroker(blockingQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        /** if size of buffer full
         */
        if (topicBuffer.size() == 100) {
            topicBufferTransient = topicBuffer;
            blockingQueue.add(topicBufferTransient);

            // sending the transient buffer via new thread
            new Thread(sendTopicBufferBQ).start();//1//////////////

            topicBuffer = new CopyOnWriteArrayList<SerializedKeyValue>();
        }

        //chk later- blocking q use
//        Thread sendingToBroker = new Thread(new SendToBroker(blockingQueue.take()));
//        sendingToBroker.start();
    }

//    private void sender() {
////        Timer timer = new Timer
//    }

    private void sendToBroker(List<SerializedKeyValue> record) {

    }




}