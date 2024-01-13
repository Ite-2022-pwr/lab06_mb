package pwr.ite.bedrylo.keeper.logic;

import lombok.SneakyThrows;
import pwr.ite.bedrylo.dataModule.dto.CommodityDto;
import pwr.ite.bedrylo.dataModule.dto.ReceiptDto;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.Commodity;
import pwr.ite.bedrylo.dataModule.model.data.Order;
import pwr.ite.bedrylo.dataModule.model.data.Receipt;
import pwr.ite.bedrylo.dataModule.model.data.User;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.ResponseType;
import pwr.ite.bedrylo.dataModule.repository.CommodityRepository;
import pwr.ite.bedrylo.dataModule.repository.ReceiptRepository;
import pwr.ite.bedrylo.dataModule.repository.UserRepository;
import pwr.ite.bedrylo.dataModule.repository.implementations.commodity.CommodityRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.repository.implementations.receipt.ReceiptRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.repository.implementations.user.UserRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.service.CommodityService;
import pwr.ite.bedrylo.dataModule.service.ReceiptService;
import pwr.ite.bedrylo.dataModule.service.UserService;
import pwr.ite.bedrylo.networking.RequestHandler;
import pwr.ite.bedrylo.networking.Util;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

//! this code is definitely not a clean code, but somehow it works and that's all that matters

public class KeeperLogic implements RequestHandler {

    private static RequestHandler instance;

    private static UserService userService;

    private static UserRepository userRepository;
    
    private static CommodityService commodityService;
    
    private static CommodityRepository commodityRepository;
    
    private static ReceiptService receiptService;
    
    private static ReceiptRepository receiptRepository;
    
    private static Queue<Order> orderQueue;
    

    public static RequestHandler getInstance() {
        if (instance == null) {
            instance = new KeeperLogic();
        }
        userRepository = new UserRepositoryJPAImplementation();
        userService = UserService.getInstance();
        commodityRepository = new CommodityRepositoryJPAImplementation();
        commodityService = CommodityService.getInstance();
        receiptRepository = new ReceiptRepositoryJPAImplementation();
        receiptService = ReceiptService.getInstance();
        orderQueue = new ArrayBlockingQueue<>(69);
        //orderQueue.add(new Order(UUID.randomUUID(), new ArrayList<>()));
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
                case "LOGIN":
                    response = new Request(ResponseType.LOGIN, login((UserDto)request.getData()));
                    break;
                case "UNREGISTER":
                    response = new Request(ResponseType.UNREGISTER, unregister((UserDto) request.getData()));
                    break;
                case "GET_INFO":
                    Object[] data = (Object[]) request.getData();
                    response = new Request(ResponseType.GET_INFO, getInfo(data[0], (Role) data[1]));
                    break;
                case "DELIVERER_GET_ORDER":
                    response = new Request(ResponseType.GET_ORDER, getOrder());
                    break;
                case "DELIVERER_RETURN_ORDER":
                case "SELLER_RETURN_ORDER":
                    response = new Request(ResponseType.RETURN_ORDER, returnOrder((Order) request.getData()));
                    break;
                case "CUSTOMER_GET_OFFER":
                    response = new Request(ResponseType.GET_OFFER, getOffer());
                    break;
                case "CUSTOMER_PUT_ORDER":
                    response = new Request(ResponseType.PUT_ORDER, putOrder((Order) request.getData()));
                    break;
                case "CUSTOMER_GET_RECEIPTS":
                    response = new Request(ResponseType.GET_RECEIPTS, getReceipsts((UserDto) request.getData()));
                    break;
                default:
                    System.out.println("nieznana akcja");
                    response = new Request(ResponseType.WRONG_REQUEST, null);
                    break;
            }
            buffer = Util.serialize(response);
            client.write(buffer);
            buffer.clear();
        }
    }

    private List<ReceiptDto> getReceipsts(UserDto data) {
        if (data != null && data.getUuid() != null){
            List<Receipt> receipts = receiptRepository.findByUserUuid(data.getUuid());
            List<ReceiptDto> receiptDtos = receipts.stream().map(o->receiptService.createReceiptDtoFromReceipt(o)).toList();
            return receiptDtos;
        }
        return null;
    }

    private Object login(UserDto data) {
        if (data != null && data.getUuid() != null){
            User user = userRepository.findByUuid(data.getUuid());
            boolean roleMatch = user.getRole() == data.getRole();
            boolean changedHost = !user.getHost().equals(data.getHost());
            boolean changedPort = user.getPort() != data.getPort();
            if (roleMatch && ( changedHost||changedPort )) {
                user = userRepository.updateHostAndPortByUuid(data.getUuid(), data.getHost(), data.getPort());
            }
            return user != null ? userService.createDtoFromUser(user) : null;
        }
        return null;
    }

    private Object putOrder(Order order) {
        if (order == null){
            return null;
        }
        orderQueue.add(order);
        for (CommodityDto commodityDto: order.getCommodityDtos()){
            commodityRepository.updateInWarehouseByUuid(commodityDto.getUuid(), false);
        }
        return order.getCommodityDtos();
    }

    private Object getOffer() {
        List<Commodity> availableCommodities = commodityRepository.findAvailable();
        if (!availableCommodities.isEmpty())
        {
            List<CommodityDto> offer = new ArrayList<>();
            for (Commodity commodity: availableCommodities) {
                offer.add(commodityService.createCommodityDTOFromCommodity(commodity));
            }
            return offer;
        }
        return null;
    }

    private Object returnOrder(Order order) {
        for (CommodityDto commodityDto : order.getCommodityDtos()) {
            commodityRepository.updateInWarehouseByUuid(commodityDto.getUuid(), true);
        }
        return null;
    }

    private Order getOrder() {
        Order order = orderQueue.poll();
        if (order == null){
            return order;
        }
        for (CommodityDto commodityDto: order.getCommodityDtos()){
            commodityRepository.updateInWarehouseByUuid(commodityDto.getUuid(), false);
        }
        return order;
    }

    private UserDto getInfo(Object uuid, Role role) {
        UserDto userDto = null;
        if (uuid.getClass() == UUID.class){
            userDto = userService.createDtoFromUser(userRepository.findByUuid((UUID) uuid));
        } else if (uuid.getClass() == Integer.class &&(Integer) uuid == 0) {
            List<User> users = userRepository.findByRole(role);
            if (users.isEmpty()){
                return null;
            }
            for (User user: users){
                if (!user.isBusy()){
                    userDto = userService.createDtoFromUser(user);
                    return userDto;
                }
            }
        } else {
            return null;
        }
        return userDto;
    }

    private UserDto register(UserDto userDto) {
        if (userDto!= null){
            return userService.createDtoFromUser(userRepository.save(userService.createUserFromDto(userDto)));    
        }
        return null;
    }

    private UserDto unregister(UserDto userDto) {
        if (userDto!= null){
            System.out.println(userDto.getUuid());
            userRepository.delete(userDto.getUuid());
            return userDto;
        }
        return null;
        
    }
}
