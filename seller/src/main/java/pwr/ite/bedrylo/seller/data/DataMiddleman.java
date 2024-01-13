package pwr.ite.bedrylo.seller.data;

import javafx.beans.property.SimpleObjectProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pwr.ite.bedrylo.dataModule.dto.ReceiptDto;
import pwr.ite.bedrylo.dataModule.model.data.ReturningOrder;

@Data
public class DataMiddleman {
    @Setter
    @Getter
    private static SimpleObjectProperty<ReturningOrder> currentOrder = new SimpleObjectProperty<ReturningOrder>();
    
    @Setter
    @Getter
    private static SimpleObjectProperty<ReceiptDto> currentReceipt = new SimpleObjectProperty<ReceiptDto>();
}
