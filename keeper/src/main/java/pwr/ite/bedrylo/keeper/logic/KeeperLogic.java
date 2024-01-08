package pwr.ite.bedrylo.keeper.logic;

import lombok.SneakyThrows;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.User;
import pwr.ite.bedrylo.dataModule.model.request.enums.ResponseType;
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
            
            switch (request.getAction().toString()){
                case "REGISTER":
                    Request response = new Request(ResponseType.REGISTER,register((UserDto) request.getData()));
                    System.out.println("dupa\n"+response);
                    System.out.println(Util.serialize(response).getClass());
                    System.out.println(buffer);
                    buffer = Util.serialize(response);
                    System.out.println(buffer);
                    break;
                case "UNREGISTER":
                    buffer = Util.serialize(new Request(null,unregister((UserDto) request.getData())));
                    break;
                case "GET_INFO":
                    break;
                case "DELIVERER_GET_ORDER":
                    break;
                case "DELIVERER_RETURN_ORDER":
                    break;
                case "CUSTOMER_GET_OFFER":
                    break;
                case "CUSTOMER_PUT_ORDER":
                    break;
                case "SELLER_RETURN_ORDER":
                    break;
                default:
                    System.out.println("nieznana akcja");
                    break;
            }
            
            
            
            client.write(buffer);
            buffer.clear();
        }
    }
    
    private UserDto register(UserDto userDto){
        return userService.createDtoFromUser(userRepository.save(userService.createUserFromDto(userDto)));
    }
    
    private UserDto unregister(UserDto userDto){
        return null;
    }
}
