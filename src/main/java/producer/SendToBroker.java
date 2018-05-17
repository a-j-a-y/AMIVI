package producer;

import java.util.List;

/**
 * Created by minal on 16/05/18.
 */
public class SendToBroker implements Runnable {

    List<SerializedKeyValue> topicBufferTransient;

    public SendToBroker(List<SerializedKeyValue> topicBufferTransient){
        this.topicBufferTransient = topicBufferTransient;
    }
    public void run() {
        // broker's put api called here, pass transient buffer

    }



}
