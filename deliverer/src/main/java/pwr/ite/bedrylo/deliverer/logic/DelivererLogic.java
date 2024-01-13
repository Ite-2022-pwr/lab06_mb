package pwr.ite.bedrylo.deliverer.logic;

import lombok.SneakyThrows;
import pwr.ite.bedrylo.dataModule.dto.CommodityDto;
import pwr.ite.bedrylo.dataModule.model.data.Order;
import pwr.ite.bedrylo.dataModule.model.data.ReturningOrder;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.ResponseType;
import pwr.ite.bedrylo.dataModule.repository.CommodityRepository;
import pwr.ite.bedrylo.dataModule.repository.UserRepository;
import pwr.ite.bedrylo.dataModule.repository.implementations.commodity.CommodityRepositoryJPAImplementation;
import pwr.ite.bedrylo.dataModule.repository.implementations.user.UserRepositoryJPAImplementation;
import pwr.ite.bedrylo.networking.RequestHandler;
import pwr.ite.bedrylo.networking.Util;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;

public class DelivererLogic implements RequestHandler {
    
    private CommodityRepository commodityRepository = new CommodityRepositoryJPAImplementation();
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
                case "CUSTOMER_RETURN_ORDER":
                    returnOrderFromCustomer((ReturningOrder) request.getData());
                    System.out.println("Return order from customer");
                    response = new Request(ResponseType.RETURN_ORDER, request.getData());
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

    private void returnOrderFromCustomer(ReturningOrder data) {
        List<CommodityDto> commodityDtosToReturn = data.getReturningCommodityDtos();
        for (CommodityDto commodityDto : commodityDtosToReturn) {
            commodityRepository.updateInWarehouseByUuid(commodityDto.getUuid(), true);
        }
    }
}
