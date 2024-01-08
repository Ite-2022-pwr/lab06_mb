module pwr.ite.bedrylo.customer {
    requires javafx.controls;
    requires javafx.fxml;


    opens pwr.ite.bedrylo.customer to javafx.fxml;
    exports pwr.ite.bedrylo.customer;
}