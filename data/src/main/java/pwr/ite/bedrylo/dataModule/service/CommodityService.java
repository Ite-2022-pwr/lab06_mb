package pwr.ite.bedrylo.dataModule.service;

import pwr.ite.bedrylo.dataModule.dto.CommodityDto;
import pwr.ite.bedrylo.dataModule.model.data.Commodity;

public class CommodityService {
    private static CommodityService instance;

    public static CommodityService getInstance() {
        if (instance == null) {
            instance = new CommodityService();
        }
        return instance;
    }

    public Commodity createCommodityFromDTO(CommodityDto commodityDto) {
        Commodity commodity = new Commodity();
        commodity.setName(commodityDto.getName());
        if (commodityDto.getUuid() != null) {
            commodity.setUuid(commodityDto.getUuid());
        }
        if (commodityDto.getUserUuid() != null) {
            commodity.setUserUuid(commodityDto.getUserUuid());
        }
        return commodity;
    }

    public CommodityDto createCommodityDTOFtomCommodity(Commodity commodity) {
        return new CommodityDto(commodity.getName(), commodity.getUserUuid(), commodity.getUuid());
    }
}
