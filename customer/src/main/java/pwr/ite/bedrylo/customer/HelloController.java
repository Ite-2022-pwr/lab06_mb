package pwr.ite.bedrylo.customer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pwr.ite.bedrylo.networking.BaseClient;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.KeeperInterfaceActions;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        BaseClient baseClient = new BaseClient("localhost",2137);
        Platform.runLater(()->{
            try {
                baseClient.sendMessage(new Request(KeeperInterfaceActions.REGISTER, null));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        welcomeText.setText("chuj");
        
    }
}