package pwr.ite.bedrylo.networking;

import lombok.SneakyThrows;
import pwr.ite.bedrylo.dataModule.model.request.Request;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class BaseClient {
    private SocketChannel client;
    private ByteBuffer buffer;
    private String serverHost;
    private Integer serverPort;

    @SneakyThrows
    public BaseClient(String serverHost, Integer serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        try {
            this.client = SocketChannel.open(new InetSocketAddress(serverHost, serverPort));
            this.buffer = ByteBuffer.allocate(65536);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @SneakyThrows
    public void stop() throws IOException {
        this.buffer = null;
        this.client.close();

    }

    public Request sendMessage(Request request) {
        this.buffer = Util.serialize(request);
        Request response = null;
        try {
            client.write(buffer);
            buffer.clear();
            client.read(buffer);
            response = (Request) Util.fromBuffer(buffer);
            buffer.clear();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response;

    }
}
