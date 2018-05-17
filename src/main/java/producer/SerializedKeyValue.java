package producer;

/**
 * Created by minal on 21/04/18.
 */
public class SerializedKeyValue {
    private byte[] key;
    private byte[] value;
    private String topic;

    public SerializedKeyValue(byte[] key, byte[] value, String topic){
        this.key = key;
        this.value = value;
        this.topic = topic;
    }

    public byte[] getKey() {
        return key;
    }

    public byte[] getValue() {
        return value;
    }

    public String getTopic() {
        return topic;
    }
}
