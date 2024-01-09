package pwr.ite.bedrylo.keeper.logic;

import lombok.SneakyThrows;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.ResponseType;
import pwr.ite.bedrylo.dataModule.repository.UserRepository;
import pwr.ite.bedrylo.dataModule.repository.implementations.user.UserRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.service.UserService;
import pwr.ite.bedrylo.networking.RequestHandler;
import pwr.ite.bedrylo.networking.Util;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

//! this code is definitely not a clean code, but somehow it works and that's all that matters

public class KeeperLogic implements RequestHandler {

    private static RequestHandler instance;

    private static UserService userService;

    private static UserRepository userRepository;

    public static RequestHandler getInstance() {
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
        if (!client.isConnected()) {
            return;
        }
        int r = -1;
        try {
            r = client.read(buffer);
        } catch (Exception e){
            System.out.println("kaput");
            client.close();
        }
               
        if (r == -1) {
            System.out.println("kaput ale celowy");
            client.close();
        } else {
            Request request = (Request) Util.fromBuffer(buffer);
            buffer.flip();
            Request response = null;
            switch (request.getAction().toString()) {
                case "REGISTER":
                    response = new Request(ResponseType.REGISTER, register((UserDto) request.getData()));
                    break;
                case "UNREGISTER":
                    response = new Request(ResponseType.UNREGISTER, unregister((UserDto) request.getData()));
                    break;
                case "DISCONNECT":
                    
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
            buffer = Util.serialize(response);
            client.write(buffer);
            buffer.clear();
        }
    }

    private UserDto register(UserDto userDto) {
        return userService.createDtoFromUser(userRepository.save(userService.createUserFromDto(userDto)));
    }

    private UserDto unregister(UserDto userDto) {
        System.out.println(userDto.getUuid());
        userRepository.delete(userDto.getUuid());
        return userDto;
    }
}
