package pwr.ite.bedrylo.customer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.User;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;
import pwr.ite.bedrylo.dataModule.service.UserService;
import pwr.ite.bedrylo.networking.BaseClient;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.KeeperInterfaceActions;
import pwr.ite.bedrylo.networking.Util;

import java.nio.ByteBuffer;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        UserDto userDto = new UserDto(69,"testowykolega", Role.CLIENT);
        BaseClient baseClient = new BaseClient("localhost",2137);
        Platform.runLater(()->{
            try {
                Request request = new Request(KeeperInterfaceActions.REGISTER, userDto);
                System.out.println(baseClient.sendMessage(request));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        welcomeText.setText("chuj");
        
    }
}