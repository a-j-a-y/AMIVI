package producer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;



/**
 * Created by minal on 27/03/18.
 */
 public class StringSerializer implements Serializer {

    Buffer buffer = new Buffer();

    public byte[] serialize(String kval) {
        return kval.getBytes();
    }
}

//        ByteArrayOutputStream out = new ByteArrayOutputStream(buffer.MAX_BUFFER_SIZE);
//        ObjectOutputStream objOut = new ObjectOutputStream(out);
//        objOut.writeObject(kval);
//        objOut.close();

