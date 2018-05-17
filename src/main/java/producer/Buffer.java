package producer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by minal on 28/04/18.
 */
public class Buffer {
    static final int MAX_BUFFER_SIZE = 100;
    String topicName;



    /**
     * buffer structure
     */
    public void Buffer()
    {

        CopyOnWriteArrayList<SerializedKeyValue> list = new CopyOnWriteArrayList<SerializedKeyValue>();
        ConcurrentHashMap<String, CopyOnWriteArrayList<SerializedKeyValue>> map;

    }
}


