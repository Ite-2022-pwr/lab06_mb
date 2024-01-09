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
        if (commodityDto.getReceiptUuid() != null) {
            commodity.setReceiptUuid(commodityDto.getReceiptUuid());
        }
        return commodity;
    }

    public CommodityDto createCommodityDTOFtomCommodity(Commodity commodity) {
        return new CommodityDto(commodity.getName(), commodity.getReceiptUuid(), commodity.getUuid(), commodity.getPrice());
    }
}
