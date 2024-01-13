package pwr.ite.bedrylo.deliverer.data;

import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;
import pwr.ite.bedrylo.dataModule.model.data.ReturningOrder;

public class DataMiddleman {

    @Setter
    @Getter
    private static SimpleObjectProperty<ReturningOrder> currentOrder = new SimpleObjectProperty<ReturningOrder>();

}
