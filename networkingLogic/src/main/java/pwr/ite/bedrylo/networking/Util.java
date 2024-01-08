package pwr.ite.bedrylo.networking;

import java.io.*;
import java.nio.ByteBuffer;

public class Util {

    public static Object fromBuffer(ByteBuffer buffer) throws IOException,
            ClassNotFoundException {
        byte[] bytes = buffer.array();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object obj = ois.readObject();
        ois.close();
        bais.close();
        return obj;
    }

    public static ByteBuffer serialize(Object object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(65536);

        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            oos.flush();
            byte[] bytes = bos.toByteArray();
            return ByteBuffer.wrap(bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
}
