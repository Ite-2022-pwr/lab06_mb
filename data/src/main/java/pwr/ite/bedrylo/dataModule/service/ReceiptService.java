package pwr.ite.bedrylo.dataModule.service;

import pwr.ite.bedrylo.dataModule.dto.ReceiptDto;
import pwr.ite.bedrylo.dataModule.model.data.Receipt;

public class ReceiptService {

    private static ReceiptService instance = null;

    public static ReceiptService getInstance() {
        if (instance == null) {
            instance = new ReceiptService();
        }
        return instance;
    }
    
    public Receipt createReceiptFromDto(ReceiptDto receiptDto){
        Receipt receipt = new Receipt();
        if (receiptDto.getUuid() != null){
            receipt.setUuid(receiptDto.getUuid());    
        }
        receipt.setUserUuid(receiptDto.getUserUuid());
        receipt.setCommodities(receiptDto.getCommodities().stream().map(o->CommodityService.getInstance().createCommodityFromDTO(o)).toList());
        return receipt;
    }
    
    public ReceiptDto createReceiptDtoFromReceipt(Receipt receipt){
        return new ReceiptDto(receipt.getUuid(), receipt.getUserUuid(), receipt.getCommodities().stream().map(o->CommodityService.getInstance().createCommodityDTOFromCommodity(o)).toList());
    }
    
}
