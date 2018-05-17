package broker;

import java.util.Map;
import java.util.HashMap;

class BrokerProperties {

    /*
    * Map contains the Broker properties as key, value pairs. Broker properties-
    * memoryRetentionKB -- maximum amount of memory (in KB) that can be retained by the system
    * memoryRetentionMS -- maximum amount of time for which memory can be retained by the system
    * segmentRecordLimit -- maximum number of records data that can be stored in one segment of any Topic.
    * private final String brokerFilesPath = "SOME-PREDEFINED PATH TO STORE THE BROKER FILES";
    * */
    private Map<String, Long> properties = new HashMap<String, Long>();
    String memoryRetentionKB = "memoryRetentionKB";
    String MemoryRetentionMS = "MemoryRetentionMS";
    String segmentRecordLimit = "segmentRecordLimit";

    public BrokerProperties() {

        properties.put("memoryRetentionKB",0L);
        properties.put("memoryRetentionMS",0L);
        properties.put("segmentRecordLimit",10L);
//        properties.put("brokerFilesPath", "PATH-TO-CREATE-BROKER-FILES-DIRECTORY");
    }

    long setBrokerProps(String prop, long value) {
        // returns old value of the mapping or null if no mapping available.
        // Here for first time it will be 'null' because of the initial value.
        return properties.put(prop, value);

    }

    long getBrokerprops(String prop) {
        return properties.get(prop);
    }
}
