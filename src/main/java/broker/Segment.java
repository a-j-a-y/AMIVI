package broker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Segment {

    private String segmentPath;                    //full path of this segment
    private Boolean isActive;               //active or closed
    private String memoryMappedFilePath;    //full path of its memory mapped file
    private long lastReadingPosition = 0;   // ???????
    private BrokerProperties properties;
    private ByteWriter writer;   // writer object which will write the record data
    private ByteReader reader;   // reader object which will read the record data
    private int flushCount;      // keep track of number of records written
//    private final String propKey = "segmentRecordLimit";

    Segment(String segmentName, String segmentPath, String mmFilePath, BrokerProperties props) throws IOException {

        this.segmentPath = segmentPath.concat(segmentName);  //full path of this segment file
        memoryMappedFilePath = mmFilePath;
        isActive = true;
        properties = props;

        try {
            //initialize a reader and writer object for this segment
            writer = new ByteWriter();
            reader = new ByteReader(this.segmentPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create the new active-segment file
        if (createSegmentFile()) {
            System.out.println("new active-segment file created");
        } else {
            System.out.println("error creating an active-segment file");
        }
    }

    /*
    flushes the data of one Serialized record to the its own segment file.
    and returns back metadata containing flush position and offset
     */
    long flushData(OffsetRecord record) {

        //FlushMetadata metadata = new FlushMetadata(); // metadata object that will contain info about new record updation in the segment.
        long flushPosition = -1; // position in segment file where new byte[] was written.

        if (writer != null) {   // segment not full
            try {

                // flush record data to segment file

                // length 4-position right-padded
                // writes the length of data followed by the data itself
                flushPosition = writer.write(segmentPath, record.getRecord().getKey());

                // write data length and data
                writer.write(segmentPath, record.getRecord().getValue());

                // write timestamp length followed by timestamp
                writer.write(segmentPath, record.getTimeStamp().getBytes());

                // update memory mapped file
                writer.write(memoryMappedFilePath, record.getOffset(), flushPosition);

                // increase the flush count
                ++flushCount;

                if (flushCount == properties.getBrokerprops(properties.segmentRecordLimit)) { //if true -> segment full
                    isActive = false;   // set segment to inactive and ready to close, since segment is full now
                    writer = null;  // no more write allowed.
                    destroySegment();   // destroys all related files a/c to given properties and returns the """""""!!!!!!!!SIGNAL"""""
                }

            } catch (IOException e) {
                flushPosition = -2;
                e.printStackTrace();
            }
        } else {
            System.out.println("CAN'T INSERT MORE RECORDS! SEGMENT IS FULL.");
        }
        return flushPosition;
    }

    /*
    search for the data (byte-array) position in the memory-mapped-file for a given 'offset'
    and uses this position to get the byte-array from the 'segment' file.
     */
    List<SerializedRecord> readRecord(String topicName, long offset, int batchSize) throws IOException {
        long dataReadPosition = 0;   //to store the data (byte array) position for reading
        long nextReadPosition = 0; // to store the next reading position for the memory-mapped-file
        List<SerializedRecord> serializedRecords = new ArrayList<>();// = new SerializedRecord();
        List<byte[]> byteArray;

        try {
            // do this for the given batch-size since data is to be sent in the batches of records
            for (int i = 0; i < batchSize; i++) {
                // new serialized-record object
                SerializedRecord serializedRecord;

                // get the position of the record data from the memory-mapped-file of this segment
                // for the given offset
                dataReadPosition = reader.readPos(offset, nextReadPosition);///!!!!!!!!correctness-check?????????????????

                // get the required record data from the segment file and set into the serialized-record object
                byteArray = reader.readByte(dataReadPosition);

                serializedRecord = new SerializedRecord(topicName, byteArray.get(0), byteArray.get(1));

                ++offset;
                serializedRecords.add(serializedRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return serializedRecords;
    }

    private boolean createSegmentFile() throws IOException{
//        boolean status = false;
        try {
            return new File(segmentPath).createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // if new segment path was not created.
        return false;
    }

    private void destroySegment() {
        // delete files related to this segment here
    }

    boolean isSegmentActive() {
        return isActive;
    }

}
