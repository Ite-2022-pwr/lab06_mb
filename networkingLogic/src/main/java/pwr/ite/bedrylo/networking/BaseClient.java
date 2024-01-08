package pwr.ite.bedrylo.networking;

import pwr.ite.bedrylo.dataModule.model.request.Request;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class BaseClient {
    private  SocketChannel client;
    private  ByteBuffer buffer;
    private String serverHost;
    private Integer serverPort;
    
    public void stop() throws IOException {
        this.client.close();
        this.buffer = null;
    }

    public BaseClient(String serverHost, Integer serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        try {
            this.client = SocketChannel.open(new InetSocketAddress(serverHost, serverPort));
            this.buffer = ByteBuffer.allocate(65536);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(Request request) {
        this.buffer = Util.serialize(request);
        Request response = null;
        try {
            client.write(buffer);
            buffer.clear();
            client.read(buffer);
            response = (Request) Util.fromBuffer(buffer);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response.toString();

    }
}
