package broker;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

class ByteReader {

    private String  filename;

    ByteReader(String filename) {
        this.filename = filename;
    }

    /*
    reads and returns the byte array for an offset of a record given the
    writing position (retrieved from the memory-mapped-file) of the
    data (byte array) in the segment file.
     */
    List<byte[]> readByte(long readingPosition) throws IOException{
//        FileInputStream fis = null;
        byte[] len = new byte[4];
        int length = 0;
        byte[] data = null;
        List<byte[]> byteArray = new ArrayList<>();
        int bytesRead = -1;
        long skipped;
        ByteBuffer wrapped;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(filename);
            skipped = fis.skip(readingPosition);
            for (int i = 0; i < 3 ; i++) {
                bytesRead = fis.read(len);          ////chk?????
                wrapped = ByteBuffer.wrap(len);
                length = wrapped.getInt();
                data = new byte[length];
                bytesRead = fis.read(data);
                byteArray.add(data);    //1.key 2.value 3.timestamp
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return byteArray;    //in case of errors returns null
    }

    /*
    read the position of data (byte array) corresponding to the given offset value
    starts searching for the offset in the memory-mapped-file from after the given last- reading -position
    for the purpose of minimizing unnecessary reads.
     */
    long readPos(long offset, long lastReadingPosition) throws IOException{

        String line;
        String[] meta;
        long nextReadingPosition = 0;
        FileReader fr;
        BufferedReader br = null;

        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            long skipped = br.skip(lastReadingPosition);

            while ((line = br.readLine()) != null && line.length() != 0) {
                meta = line.split(",");
                if (meta[0].equals(Long.toString(offset))) {
                    nextReadingPosition = Long.parseLong(meta[1], 10);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return nextReadingPosition;
    }


}
