package pwr.ite.bedrylo.customer;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import pwr.ite.bedrylo.dataModule.dto.CommodityDto;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.Commodity;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.KeeperInterfaceActions;
import pwr.ite.bedrylo.dataModule.service.UserService;
import pwr.ite.bedrylo.networking.BaseClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static javafx.application.Platform.*;

public class CustomerController {
    @FXML
    private TextField hostTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private Button registerButtons;
    @FXML
    private Button unregisterButton;
    @FXML
    private Label activeUserIdLabel;
    @FXML
    private TextField activeUserIdTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button getOfferButton;
    @FXML
    private TableView offerTable;
    @FXML
    private Button addToOrderButton;
    @FXML
    private Button RemoveFromOrderButton;
    @FXML
    private Button putOrderButton;
    @FXML
    private TableView preOrderTable;
    @FXML
    private TableView cartTable;
    @FXML
    private Button acceptBySellerButton;
    @FXML
    private Button moveToReturnButton;
    @FXML
    private Button moveToCartButton;
    @FXML
    private TableView returnTable;
    @FXML
    private Button returnButton;
    @FXML
    private TableView receiptTable;
    @FXML
    private Button showReceiptButton;
    @FXML
    private TableView receiptContentTable;

    private BaseClient baseClient;
    
    private UserDto activeUser;
    
    private UserService userService = UserService.getInstance();
    
    private int keeperPort = 2137;
    
    private String keeperHost = "localhost";
    
    private Request latestResponse;
    
    private ObservableList<CommodityDto> availableCommodities = FXCollections.observableArrayList();
    private Property<ObservableList<CommodityDto>> offerTableProperty = new SimpleObjectProperty<>(availableCommodities);

    private ObservableList<CommodityDto> preOrderCommodities = FXCollections.observableArrayList();
    private Property<ObservableList<CommodityDto>> preOrderTableTableProperty = new SimpleObjectProperty<>(availableCommodities);

    public void initialize(){
        offerTable.itemsProperty().bind(offerTableProperty);
        preOrderTable.itemsProperty().bind(preOrderTableTableProperty);
    }

    @FXML
    private void onRegisterButtonClick() {
        activeUser = new UserDto(Integer.parseInt(portTextField.getText()), hostTextField.getText(), Role.CLIENT);
        Request request= new Request(KeeperInterfaceActions.REGISTER, activeUser);
        registerOrLogin(request);
    }

    @FXML
    private void onLoginButtonClick(){
        activeUser = new UserDto(Integer.parseInt(portTextField.getText()), hostTextField.getText(), Role.CLIENT, UUID.fromString(activeUserIdTextField.getText()));
        Request request= new Request(KeeperInterfaceActions.LOGIN, activeUser);
        registerOrLogin(request);
    }
    
    @FXML
    private void onGetOfferButtonClick(){
        Request request = new Request(KeeperInterfaceActions.CUSTOMER_GET_OFFER, null);
        runLater(() -> {
            try {
                baseClient = new BaseClient(keeperHost, keeperPort);
                latestResponse = baseClient.sendMessage(request);
                if (latestResponse.getData() != null){
                    availableCommodities.clear();
                    availableCommodities.addAll((List<CommodityDto>) latestResponse.getData());
                }
                System.out.println(latestResponse);
                baseClient.stop();
                baseClient = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void registerOrLogin(Request request) {
        runLater(() -> {
            try {
                baseClient = new BaseClient(keeperHost, keeperPort);
                latestResponse = baseClient.sendMessage(request);
                if (latestResponse.getData() != null){
                    activeUser = (UserDto) latestResponse.getData();
                    activateUiButtons();
                }
                System.out.println(latestResponse);
                baseClient.stop();
                baseClient = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    
    private void activateUiButtons() {
        registerButtons.setDisable(true);
        hostTextField.setDisable(true);
        portTextField.setDisable(true);
        activeUserIdTextField.setDisable(true);
        unregisterButton.setDisable(false);
        getOfferButton.setDisable(false);
        addToOrderButton.setDisable(false);
        RemoveFromOrderButton.setDisable(false);
        putOrderButton.setDisable(false);
        acceptBySellerButton.setDisable(false);
        moveToReturnButton.setDisable(false);
        moveToCartButton.setDisable(false);
        returnButton.setDisable(false);
        showReceiptButton.setDisable(false);
    }

}
