package pwr.ite.bedrylo;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public interface RequestHandler {
    public void processRequest(ByteBuffer buffer, SelectionKey key);
}
