package pwr.ite.bedrylo.keeper.logic;

import lombok.SneakyThrows;
import pwr.ite.bedrylo.networking.RequestHandler;
import pwr.ite.bedrylo.networking.Util;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class KeeperLogic implements RequestHandler {
    
    private static RequestHandler instance;
    
    public static RequestHandler getInstance(){
        if (instance == null) {
            instance = new KeeperLogic();
        }
        return instance;
    }
    @SneakyThrows
    @Override
    public void processRequest(ByteBuffer buffer, SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        int r = client.read(buffer);
        if (r == -1) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        }
        else {
            Request request = (Request) Util.fromBuffer(buffer);
            System.out.println(request);
            buffer.flip();
            client.write(buffer);
            buffer.clear();
        }
    }
}
