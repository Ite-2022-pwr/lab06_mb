package pwr.ite.bedrylo.seller.logic;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pwr.ite.bedrylo.dataModule.dto.CommodityDto;
import pwr.ite.bedrylo.dataModule.dto.ReceiptDto;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.Receipt;
import pwr.ite.bedrylo.dataModule.model.data.ReturningOrder;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.KeeperInterfaceActions;
import pwr.ite.bedrylo.dataModule.model.request.enums.ResponseType;
import pwr.ite.bedrylo.dataModule.repository.CommodityRepository;
import pwr.ite.bedrylo.dataModule.repository.ReceiptRepository;
import pwr.ite.bedrylo.dataModule.repository.UserRepository;
import pwr.ite.bedrylo.dataModule.repository.implementations.commodity.CommodityRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.repository.implementations.receipt.ReceiptRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.repository.implementations.user.UserRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.service.CommodityService;
import pwr.ite.bedrylo.dataModule.service.ReceiptService;
import pwr.ite.bedrylo.networking.BaseClient;
import pwr.ite.bedrylo.networking.RequestHandler;
import pwr.ite.bedrylo.networking.Util;
import pwr.ite.bedrylo.seller.data.DataMiddleman;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class SellerLogic implements RequestHandler {
    
    @Getter
    @Setter
    private UserDto activeUser;
    
    private String keeperHost = "localhost";
    
    private int keeperPort = 2137;
    
    
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
                case "CUSTOMER_ACCEPT_ORDER":
                    ReceiptDto receiptDto = acceptOrderFromCustomer((ReturningOrder) request.getData());
                    System.out.println("Accept order from customer");
                    if (receiptDto != null) {
                        response = new Request(ResponseType.ACCEPT_ORDER, receiptDto);
                    } else {
                        response = new Request(ResponseType.WRONG_REQUEST, null);
                    }
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

    private ReceiptDto acceptOrderFromCustomer(ReturningOrder data) {
        if (data != null) {
            try {
                BaseClient client = new BaseClient(keeperHost, keeperPort);
                Request latestResponse = client.sendMessage(new Request(KeeperInterfaceActions.SELLER_RETURN_ORDER, new Object[] {activeUser, data}));
                System.out.println(latestResponse);
                DataMiddleman.getCurrentReceipt().set((ReceiptDto) latestResponse.getData());
                client.stop();
                client = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
