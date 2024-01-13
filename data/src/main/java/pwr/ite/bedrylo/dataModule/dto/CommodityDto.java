package pwr.ite.bedrylo.dataModule.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pwr.ite.bedrylo.dataModule.model.data.Commodity;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Commodity}
 */
@Data
@Accessors(chain = true)
public class CommodityDto implements Serializable {
    private UUID uuid;
    private String name;
    private ReceiptDto receiptDto;
    private double price;
    private boolean inWarehouse;

    public CommodityDto(String name, ReceiptDto receiptDto, UUID uuid, double price) {
        this.name = name;
        this.receiptDto = receiptDto;
        this.uuid = uuid;
        this.price = price;
    }

    public CommodityDto(String name, UUID uuid, double price) {
        this.name = name;
        this.uuid = uuid;
        this.price = price;
    }
}