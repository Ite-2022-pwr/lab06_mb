module pwr.ite.bedrylo.seller {
    requires javafx.controls;
    requires javafx.fxml;
    requires networkingLogic;
    requires lombok;
    requires data;


    opens pwr.ite.bedrylo.seller to javafx.fxml;
    exports pwr.ite.bedrylo.seller;
    exports pwr.ite.bedrylo.seller.controller;
    opens pwr.ite.bedrylo.seller.controller to javafx.fxml;
    exports pwr.ite.bedrylo.seller.logic;
    opens pwr.ite.bedrylo.seller.logic to javafx.fxml;
}