package pwr.ite.bedrylo.seller.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pwr.ite.bedrylo.dataModule.dto.ReceiptDto;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.CustomerInterfaceActions;
import pwr.ite.bedrylo.dataModule.model.request.enums.KeeperInterfaceActions;
import pwr.ite.bedrylo.networking.BaseClient;
import pwr.ite.bedrylo.networking.BaseServer;
import pwr.ite.bedrylo.seller.data.DataMiddleman;
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
        observableReceipt();
        host = hostField.getText();
        port = Integer.parseInt(portField.getText());
        Request request = new Request(KeeperInterfaceActions.REGISTER, new UserDto(port, host, Role.SELLER));
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
    public void onStopButtonClick() {
        infoTextLabel.setText("Seller is stopped");
        startButton.setDisable(false);
        server = null;
        Request request = new Request(KeeperInterfaceActions.UNREGISTER, activeUser);
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
    }

    private void observableReceipt() {
        DataMiddleman.getCurrentReceipt().addListener(new ChangeListener<ReceiptDto>() {
            @Override
            public void changed(ObservableValue<? extends ReceiptDto> observable, ReceiptDto oldValue, ReceiptDto newValue) {
                runLater(() -> {
                    try {
                        Request request = new Request(KeeperInterfaceActions.GET_INFO, new Object[]{newValue.getUserUuid(), null});
                        client = new BaseClient(keeperHost, keeperPort);
                        latestResponse = client.sendMessage(request);
                        sendReceiptToCustomer((UserDto) latestResponse.getData());
                        System.out.println(latestResponse);
                        client.stop();
                        client = null;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }

        });
    }

    private void sendReceiptToCustomer(UserDto data) {
        if (data != null) {
            try {
                BaseClient client = new BaseClient(data.getHost(), data.getPort());
                Request latestResponse = client.sendMessage(new Request(CustomerInterfaceActions.SELLER_RETURN_RECEIPT, DataMiddleman.getCurrentReceipt().get()));
                System.out.println(latestResponse);
                client.stop();
                client = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}