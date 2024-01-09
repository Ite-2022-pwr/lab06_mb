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
    private String name;
    private UUID receiptUuid;
    private UUID uuid;
    private double price;


    public CommodityDto(String name, UUID receiptUuid, UUID uuid, double price) {
        this.name = name;
        this.receiptUuid = receiptUuid;
        this.uuid = uuid;
        this.price = price;
    }
}