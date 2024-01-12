package pwr.ite.bedrylo.dataModule.model.data;


import lombok.Data;
import pwr.ite.bedrylo.dataModule.dto.CommodityDto;

import java.util.List;
import java.util.UUID;

@Data
public class ReturningOrder extends Order{
    
    private List<CommodityDto> returningCommodityDtos;
    
    public ReturningOrder(){
        
    }
    
    public ReturningOrder(UUID uuid, List<CommodityDto> commodityDtos, List<CommodityDto> returningCommodityDtos){
        this.setUserUuid(uuid);
        this.setCommodityDtos(commodityDtos);
        this.setReturningCommodityDtos(returningCommodityDtos);
    }
    
}
