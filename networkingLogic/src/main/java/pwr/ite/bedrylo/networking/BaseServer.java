package pwr.ite.bedrylo.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class BaseServer implements Runnable {

    private Integer port;
    private String host;
    private RequestHandler requestHandler;
    private Thread thread;
    private String name;

    public BaseServer(Integer port, String host, RequestHandler requestHandler) {
        this.port = port;
        this.host = host;
        this.name = host + port.toString();
        this.requestHandler = requestHandler;
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {

        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    public void startServer() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(this.host, this.port));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(65536);

        System.out.println("gówno ożyło");

        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {

                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                }

                if (key.isReadable()) {
                    requestHandler.processRequest(buffer, key);
                }
                iter.remove();
            }
        }


    }

    @Override
    public void run() {
        try {
            System.out.println("wstaje");
            this.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Thread.currentThread().interrupt();
            System.out.println("zdechło");
        }
    }

    public void start(boolean runFromConsole) {
        System.out.println("Starting " + name);
        if (thread == null) {
            thread = new Thread(this, name);
            if (!runFromConsole) {
                thread.setDaemon(true);
            }
            thread.start();
        }
    }
}
