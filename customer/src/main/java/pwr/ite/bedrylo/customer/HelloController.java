package pwr.ite.bedrylo.customer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.SneakyThrows;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.KeeperInterfaceActions;
import pwr.ite.bedrylo.networking.BaseClient;

import java.util.UUID;

public class HelloController {
    @FXML
    private Label welcomeText;
    
    private BaseClient baseClient;

    @SneakyThrows
    @FXML
    protected void onHelloButtonClick() {
        UserDto userDto = new UserDto(420, "test3", Role.CLIENT, UUID.randomUUID());
         baseClient = new BaseClient("localhost", 2137);
        Platform.runLater(() -> {
            try {
                Request request = new Request(KeeperInterfaceActions.REGISTER, userDto);
                System.out.println(baseClient.sendMessage(request));
                baseClient.stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        welcomeText.setText("chuj");
    }

    @FXML
    public void onByeButtonClick() {
        Platform.runLater(() -> {
            try {
                baseClient = new BaseClient("localhost", 2137);
                System.out.println(baseClient.sendMessage(new Request(KeeperInterfaceActions.CUSTOMER_GET_OFFER, null)));
                baseClient.stop();
            }catch (Exception e){
                System.out.println("chujek");
            }
                
        });
    }
}