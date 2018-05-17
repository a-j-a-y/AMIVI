package broker;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

class ByteWriter {
    private static long dataFlushPosition = 0;

    // write into the segment file
    long write(String filename, byte[] data) throws IOException {
        FileOutputStream fos = null;
        long pos = -1;

        try {
            fos = new FileOutputStream(filename, true);

            // write length of the data
            fos.write((String.format("%1$-4s", Integer.toString(data.length))).getBytes());          ////////???????????chk with example int==4bytes or not in file for smaller ints

            // write the data itself
            fos.write(data);

            fos.flush();

            pos = dataFlushPosition;

            dataFlushPosition = fos.getChannel().position();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }

        return pos;
    }

    // write into the memory mapped file
    void write(String filename, long offset, long flushPosition) throws IOException {
        BufferedWriter bw = null;
        FileWriter fw;
        String metadata = offset+","+flushPosition;
        try {
            fw = new FileWriter(filename, true);
            bw = new BufferedWriter(fw);
            bw.write(metadata);
            bw.newLine();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
        // do fw need to be closed???????????
    }
}
