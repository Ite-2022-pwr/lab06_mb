module pwr.ite.bedrylo {
    requires javafx.controls;
    requires javafx.fxml;

    opens pwr.ite.bedrylo to javafx.fxml;
    exports pwr.ite.bedrylo;
}
