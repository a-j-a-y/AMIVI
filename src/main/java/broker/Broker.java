package broker;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public class Broker {

    private final String brokerFilesPath = "brokerFilesPath"; //private final String brokerFilesPath = "SOME-PREDEFINED PATH TO STORE THE BROKER FILES"; move it in Broker-Properties

    private BrokerProperties properties;    //

    private ExecutorService threadPool;     //A pool of threads such that 1 thread for each topic ??Use singleThreadExecutor[] or cachedThread pool for simplicity.

    private OffsetGenerator offsetGenerator = new OffsetGenerator();    // generates the offset for each topic

    private RecordBufferReader recordBufferReader = new RecordBufferReader();

    private RecordBufferWriter recordBufferWriter = new RecordBufferWriter();

    private static Broker brokerNode = null;

    private Broker() {

    }

    public static Broker getBrokerInstanceNode() {
        if (brokerNode == null) {
            brokerNode = new Broker();
        }

        return brokerNode;
    }

    public void setProperties(BrokerProperties properties) {
        this.properties = properties;

        if (createBrokerFileDirectory()) {
            System.out.println("\nBroker files path created!\nBroker is Up and running....\n");
        }
    }

    public List<SerializedRecord> getRecord(String topicName, int clientId) {
        final int defaultBatchSize = 100;

        return getRecord(topicName, clientId, defaultBatchSize);
    }

    public List<SerializedRecord> getRecord(String topicName, int clientId, int batchSize) {

//        List<SerializedRecord> serializedRecords;

        return recordBufferReader.readRecordBuffer(recordBufferWriter.getTopics(), topicName, clientId, batchSize);

//        SerializedRecord serializedRecord = new SerializedRecord(topicName, batchSize);

    }

    // originally return type recordMetadata[]
    public void putRecord(List<SerializedRecord> serializedRecords) {

        List<OffsetRecord> offsetRecords;

        offsetRecords = offsetGenerator.generate(serializedRecords);

        recordBufferWriter.addRecords(offsetRecords);

    }

//    private ArrayList<RecordMetadata> commitRecord(List<SerializedRecord> records) {
//
//    }


    private boolean createBrokerFileDirectory() {
        File file = new File(brokerFilesPath);

        return file.mkdirs();   // true if path successfully creates else false
    }
}
