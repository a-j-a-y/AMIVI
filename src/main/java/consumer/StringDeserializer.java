package consumer;

import java.util.List;

public class StringDeserializer implements Deserializer {

    public String deserialize(byte[] kval){
        return new String(kval);
    }
}