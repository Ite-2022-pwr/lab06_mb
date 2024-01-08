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
        System.out.println(obj);
        ois.close();
        bais.close();
        return obj;
    }

    public static ByteBuffer serialize(Object object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(65536);
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            oos.flush();
            oos.close();
            //byte[] bytes = bos.toByteArray();
            byte[] bytes = new byte[65536];
            byte[] temp = bos.toByteArray();
            for (int i = 0; i < temp.length; i++) {
                bytes[i] = temp[i];
            }
            return ByteBuffer.wrap(bytes, 0, 65536);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
}
