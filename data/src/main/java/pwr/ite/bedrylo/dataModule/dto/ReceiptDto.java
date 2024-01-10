package pwr.ite.bedrylo.dataModule.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pwr.ite.bedrylo.dataModule.dto.CommodityDto;
import pwr.ite.bedrylo.dataModule.model.data.Receipt;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link Receipt}
 */
@Data
@Accessors(chain = true)
public class ReceiptDto implements Serializable {
    private UUID uuid;
    private UUID userUuid;
    private List<CommodityDto> commodities;
    
    public ReceiptDto(UUID uuid, UUID userUuid, List<CommodityDto> commodities){
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.commodities = commodities;
    }
    
    public ReceiptDto(UUID userUuid, List<CommodityDto> commodities){
        this.userUuid = userUuid;
        this.commodities = commodities;
    }
}