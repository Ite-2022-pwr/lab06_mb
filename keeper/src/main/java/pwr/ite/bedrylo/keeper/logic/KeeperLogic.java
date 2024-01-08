package pwr.ite.bedrylo.keeper.logic;

import lombok.SneakyThrows;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.User;
import pwr.ite.bedrylo.dataModule.repository.UserRepository;
import pwr.ite.bedrylo.dataModule.repository.implementations.user.UserRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.service.UserService;
import pwr.ite.bedrylo.networking.RequestHandler;
import pwr.ite.bedrylo.networking.Util;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class KeeperLogic implements RequestHandler {
    
    private static RequestHandler instance;
    
    private static UserService userService;
    
    private static UserRepository userRepository;
    
    public static RequestHandler getInstance(){
        if (instance == null) {
            instance = new KeeperLogic();
        }
        userRepository = new UserRepositoryJPAImplementation();
        userService = UserService.getInstance();
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
            Request request =(Request) Util.fromBuffer(buffer);
            System.out.println(request);
            buffer.flip();
            System.out.println(request.getData());
            switch (request.getAction().toString()){
                case "REGISTER":
                    register((UserDto) request.getData());
                    break;
                case "UNREGISTER":
                    System.out.println("chuj");
                    break;
                default:
                    System.out.println("nieznana akcja");
                    break;
            }
            client.write(buffer);
            buffer.clear();
        }
    }
    
    private void register(UserDto userDto){
        userRepository.save(userService.createUserFromDto(userDto));
    }
}
