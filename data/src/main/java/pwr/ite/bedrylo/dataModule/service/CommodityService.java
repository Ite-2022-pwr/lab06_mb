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
        commodity.setUuid(commodityDto.getUuid());
        commodity.setName(commodityDto.getName());
        commodity.setInWarehouse(true);
        if (commodityDto.getReceiptDto() != null) {
            commodity.setReceipt(ReceiptService.getInstance().createReceiptFromDto(commodityDto.getReceiptDto()));
        }
        commodity.setPrice(commodityDto.getPrice());
        return commodity;
    }

    public CommodityDto createCommodityDTOFromCommodity(Commodity commodity) {
        return new CommodityDto(commodity.getName(), commodity.getUuid(), commodity.getPrice());
    }
}
