package pwr.ite.bedrylo.networking;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public interface RequestHandler {
    void processRequest(ByteBuffer buffer, SelectionKey key);
}
