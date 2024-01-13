package pwr.ite.bedrylo.deliverer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.Order;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.CustomerInterfaceActions;
import pwr.ite.bedrylo.dataModule.model.request.enums.KeeperInterfaceActions;
import pwr.ite.bedrylo.deliverer.logic.DelivererLogic;
import pwr.ite.bedrylo.networking.BaseClient;
import pwr.ite.bedrylo.networking.BaseServer;

import static javafx.application.Platform.runLater;

public class DelivererController {

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
    @FXML
    private Button takeOrderButton;
    @FXML
    private Button getCustomerInfoButton;
    @FXML
    private Button deliverOrderButton;

    private BaseClient client;

    private String keeperHost = "localhost";

    private int keeperPort = 2137;

    private String host;

    private int port;

    private BaseServer server;

    private DelivererLogic requestHandler = new DelivererLogic();

    private UserDto activeUser;

    private UserDto activeCustomer;

    private Request latestResponse;

    private Order activeOrder;

    public void initialize() {
        takeOrderButton.setDisable(true);
        getCustomerInfoButton.setDisable(true);
        deliverOrderButton.setDisable(true);
    }

    @FXML
    private void onStartButtonClick() {
        infoTextLabel.setText("Deliverer is running");
        startButton.setDisable(true);
        takeOrderButton.setDisable(false);
        host = hostField.getText();
        port = Integer.parseInt(portField.getText());
        Request request = new Request(KeeperInterfaceActions.REGISTER, new UserDto(port, host, Role.DELIVERER));
        runLater(() -> {
            try {
                client = new BaseClient(keeperHost, keeperPort);
                latestResponse = client.sendMessage(request);
                if (latestResponse.getData() != null) {
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
    private void onStopButtonClick() {
        infoTextLabel.setText("Deliverer is stopped");
        startButton.setDisable(false);
        takeOrderButton.setDisable(true);
        server = null;
        Request request = new Request(KeeperInterfaceActions.UNREGISTER, activeUser);
        runLater(() -> {
            try {
                client = new BaseClient(keeperHost, keeperPort);
                latestResponse = client.sendMessage(request);
                System.out.println(latestResponse);
                client.stop();
                client = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void onTakeOrderButtonClick() {
        Request request = new Request(KeeperInterfaceActions.DELIVERER_GET_ORDER, activeUser);
        runLater(() -> {
            try {
                client = new BaseClient(keeperHost, keeperPort);
                latestResponse = client.sendMessage(request);
                if (latestResponse.getData() != null) {
                    activeOrder = (Order) latestResponse.getData();
                    if (activeOrder != null) {
                        takeOrderButton.setDisable(true);
                        getCustomerInfoButton.setDisable(false);
                    }
                }
                System.out.println(latestResponse);
                client.stop();
                client = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    @FXML
    public void onGetCustomerInfoButtonClick() {
        Request request = new Request(KeeperInterfaceActions.GET_INFO, new Object[]{activeOrder.getUserUuid(), null});
        runLater(() -> {
            try {
                client = new BaseClient(keeperHost, keeperPort);
                latestResponse = client.sendMessage(request);
                if (latestResponse.getData() != null) {
                    activeCustomer = (UserDto) latestResponse.getData();
                    getCustomerInfoButton.setDisable(true);
                    deliverOrderButton.setDisable(false);
                }
                System.out.println(latestResponse);
                client.stop();
                client = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void onDeliverOrderButtonClick() {
        Request request = new Request(CustomerInterfaceActions.DELIVERER_PUT_ORDER, activeOrder);
        runLater(() -> {
            try {
                client = new BaseClient(activeCustomer.getHost(), activeCustomer.getPort());
                latestResponse = client.sendMessage(request);
                if (latestResponse.getData() != null) {
                    activeOrder = null;
                    deliverOrderButton.setDisable(true);
                    takeOrderButton.setDisable(false);
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