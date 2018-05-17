package broker;

import java.util.ArrayList;
import java.util.List;

class RecordMetadata {
    private String topicName;
    private List<Long> offsets = new ArrayList<>();

    RecordMetadata(String topicName) {
        this.topicName = topicName;
    }

    void addOffset(long offset) {
        offsets.add(offset);
    }

    public String getTopicName() {
        return topicName;
    }

    public List<Long> getOffsets() {
        return offsets;
    }
}
