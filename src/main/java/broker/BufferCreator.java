package broker;

import java.util.Queue;
import java.util.concurrent.Callable;

public class BufferCreator implements Callable {
    private final Queue<SerializedRecord> recordBuffer;
    private SerializedRecord record;

    BufferCreator(Queue<SerializedRecord> recordBuffer, SerializedRecord record) {
        this.recordBuffer = recordBuffer;
        this.record = record;
    }

    @Override
    public Queue<SerializedRecord> call() {
        recordBuffer.add(record);
        return recordBuffer;
    }
}
