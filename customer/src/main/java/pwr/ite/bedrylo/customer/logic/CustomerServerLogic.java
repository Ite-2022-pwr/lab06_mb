package pwr.ite.bedrylo.customer.logic;

import lombok.SneakyThrows;
import pwr.ite.bedrylo.customer.data.DataMiddleman;
import pwr.ite.bedrylo.dataModule.dto.CommodityDto;
import pwr.ite.bedrylo.dataModule.dto.ReceiptDto;
import pwr.ite.bedrylo.dataModule.model.data.Order;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.ResponseType;
import pwr.ite.bedrylo.dataModule.service.CommodityService;
import pwr.ite.bedrylo.dataModule.service.ReceiptService;
import pwr.ite.bedrylo.dataModule.service.UserService;
import pwr.ite.bedrylo.networking.RequestHandler;
import pwr.ite.bedrylo.networking.Util;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;

public class CustomerServerLogic implements RequestHandler {

    private static RequestHandler instance;

    private static UserService userService;

    private static CommodityService commodityService;

    private static ReceiptService receiptService;


    public static RequestHandler getInstance() {
        if (instance == null) {
            instance = new CustomerServerLogic();
        }

        userService = UserService.getInstance();

        commodityService = CommodityService.getInstance();

        receiptService = ReceiptService.getInstance();
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
        } catch (Exception e) {
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
                case "DELIVERER_PUT_ORDER":
                    putOrderToCart((Order) request.getData());
                    System.out.println("DELIVERER_PUT_ORDER");
                    response = new Request(ResponseType.PUT_ORDER, request.getData());
                    break;
                case "SELLER_RETURN_RECEIPT":
                    if (request.getData() != null) {
                        addReceiptToList((ReceiptDto) request.getData());
                        System.out.println("SELLER_RETURN_RECEIPT");
                        DataMiddleman.clearCart();
                        DataMiddleman.clearReturnCommodities();
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

    private void addReceiptToList(ReceiptDto data) {
        DataMiddleman.getReceipts().add(data);
        System.out.println("addReceiptToList");
    }

    private void putOrderToCart(Order data) {
        List<CommodityDto> commodities = data.getCommodityDtos();
        DataMiddleman.getCartCommodities().addAll(commodities);
        System.out.println("putOrderToCart");
    }


}
