package pwr.ite.bedrylo.dataModule.model.data;

import lombok.Data;
import pwr.ite.bedrylo.dataModule.dto.CommodityDto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class Order implements Serializable {
    private UUID userUuid;
    private List<CommodityDto> commodityDtos;
    
    public Order(UUID userUuid, List<CommodityDto> commodityDtos){
        this.userUuid = userUuid;
        this.commodityDtos = commodityDtos;
    }
    
}
