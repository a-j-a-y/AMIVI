package consumer;


public class DeserializedKeyValue {
    private String topicName;
    private String key;
    private String value;


    public DeserializedKeyValue(String topicName, String key, String value){
        this.topicName = topicName;
        this.key = key;
        this.value = value;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}