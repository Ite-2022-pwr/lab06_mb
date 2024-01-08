module pwr.ite.bedrylo.keeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires serverLogic;
    requires data;
    requires static lombok;


    opens pwr.ite.bedrylo.keeper to javafx.fxml;
    exports pwr.ite.bedrylo.keeper;
    exports pwr.ite.bedrylo.keeper.controller;
    opens pwr.ite.bedrylo.keeper.controller to javafx.fxml;
}