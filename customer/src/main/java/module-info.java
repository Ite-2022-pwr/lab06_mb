module pwr.ite.bedrylo.customer {
    requires javafx.controls;
    requires javafx.fxml;
    requires networkingLogic;
    requires data;
    requires static lombok;


    opens pwr.ite.bedrylo.customer to javafx.fxml;
    exports pwr.ite.bedrylo.customer;
    exports pwr.ite.bedrylo.customer.controller;
    opens pwr.ite.bedrylo.customer.controller to javafx.fxml;
}