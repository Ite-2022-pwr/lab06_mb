package pwr.ite.bedrylo.customer.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import pwr.ite.bedrylo.dataModule.dto.CommodityDto;
import pwr.ite.bedrylo.dataModule.dto.ReceiptDto;

@Data
public class DataMiddleman {

    private static ObservableList<CommodityDto> cartCommodities = FXCollections.observableArrayList();

    private static ObservableList<ReceiptDto> receipts = FXCollections.observableArrayList();
    public static ObservableList<CommodityDto> getCartCommodities() {
        return cartCommodities;
    }
    
    public static void addCommodity(CommodityDto commodity){
        cartCommodities.add(commodity);
    }
    
    public static void removeCommodity(CommodityDto commodity){
        cartCommodities.remove(commodity);
    }
    
    public static void clearCart(){
        cartCommodities.clear();
    }
    
    public static ObservableList<ReceiptDto> getReceipts() {
        return receipts;
    }
    
    public static void addReceipt(ReceiptDto receipt){
        receipts.add(receipt);
    }
    
    public static void removeReceipt(ReceiptDto receipt){
        receipts.remove(receipt);
    }
    
    public static void clearReceipts(){
        receipts.clear();
    }
    
}
