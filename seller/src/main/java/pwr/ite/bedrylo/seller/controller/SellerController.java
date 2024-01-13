package pwr.ite.bedrylo.seller.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.KeeperInterfaceActions;
import pwr.ite.bedrylo.networking.BaseClient;
import pwr.ite.bedrylo.networking.BaseServer;
import pwr.ite.bedrylo.networking.RequestHandler;
import pwr.ite.bedrylo.seller.logic.SellerLogic;

import static javafx.application.Platform.runLater;

public class SellerController {

    @FXML
    private Label infoTextLabel;
    @FXML
    private TextField hostField;
    @FXML
    private TextField portField;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    private BaseClient client;

    private String keeperHost = "localhost";

    private int keeperPort = 2137;

    private String host;

    private int port;

    private BaseServer server;

    private SellerLogic requestHandler = new SellerLogic();

    private UserDto activeUser;

    private UserDto activeCustomer;
    
    private Request latestResponse;

    @FXML
    private void onStartButtonClick() {
        infoTextLabel.setText("Seller is running");
        startButton.setDisable(true);
        host = hostField.getText();
        port = Integer.parseInt(portField.getText());
        Request request = new Request(KeeperInterfaceActions.REGISTER, new UserDto(port, host, Role.SELLER));
        runLater(() -> {
            try {
                client = new BaseClient(keeperHost, keeperPort);
                latestResponse = client.sendMessage(request);
                if (latestResponse.getData() != null){
                    activeUser = (UserDto) latestResponse.getData();
                    requestHandler.setActiveUser(activeUser);
                }
                System.out.println(latestResponse);
                client.stop();
                client = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        server = new BaseServer(port, host, requestHandler);
        try {
            server.start(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onStopButtonClick() {
        infoTextLabel.setText("Seller is stopped");
        startButton.setDisable(false);
        server = null;
        Request request = new Request(KeeperInterfaceActions.UNREGISTER, new UserDto(port, host, Role.SELLER));
        runLater(() -> {
            try {
                client = new BaseClient(keeperHost, keeperPort);
                latestResponse = client.sendMessage(request);
                if (latestResponse.getData() != null){
                    activeUser = (UserDto) latestResponse.getData();
                    requestHandler.setActiveUser(activeUser);
                }
                System.out.println(latestResponse);
                client.stop();
                client = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}