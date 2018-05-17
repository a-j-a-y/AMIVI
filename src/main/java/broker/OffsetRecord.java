package broker;

import java.text.SimpleDateFormat;
import java.util.Date;

class OffsetRecord {
    private SerializedRecord record;    // serialized record from producer
    private String timeStamp;   // time stamp of this object creation
    private long offset;    // offset of this record

    OffsetRecord(SerializedRecord record, long offset) {
        this.record = record;
        this.offset = offset;
        timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    SerializedRecord getRecord() {
        return record;
    }

    public long getOffset() { return offset; }

    public String getTimeStamp() {
        return timeStamp;
    }
}
