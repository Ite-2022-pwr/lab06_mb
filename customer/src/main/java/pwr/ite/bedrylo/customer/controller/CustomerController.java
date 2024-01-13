package pwr.ite.bedrylo.customer.controller;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pwr.ite.bedrylo.customer.data.DataMiddleman;
import pwr.ite.bedrylo.customer.logic.CustomerServerLogic;
import pwr.ite.bedrylo.dataModule.dto.CommodityDto;
import pwr.ite.bedrylo.dataModule.dto.ReceiptDto;
import pwr.ite.bedrylo.dataModule.dto.UserDto;
import pwr.ite.bedrylo.dataModule.model.data.Order;
import pwr.ite.bedrylo.dataModule.model.data.ReturningOrder;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;
import pwr.ite.bedrylo.dataModule.model.request.Request;
import pwr.ite.bedrylo.dataModule.model.request.enums.DelivererInterfaceActions;
import pwr.ite.bedrylo.dataModule.model.request.enums.KeeperInterfaceActions;
import pwr.ite.bedrylo.dataModule.model.request.enums.SellerInterfaceActions;
import pwr.ite.bedrylo.dataModule.service.UserService;
import pwr.ite.bedrylo.networking.BaseClient;
import pwr.ite.bedrylo.networking.BaseServer;
import pwr.ite.bedrylo.networking.RequestHandler;

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
    @FXML
    private Button receiptTableRefreshButton;
    @FXML
    private Button refreshCartButton;

    private BaseClient baseClient;
    
    private UserDto activeUser;
    
    private UserDto availableSeller;
    
    private UserDto availableDeliverer;
    
    private UserService userService = UserService.getInstance();
    
    private BaseServer baseServer;
    
    private RequestHandler customerServerLogic = CustomerServerLogic.getInstance();
    
    private int keeperPort = 2137;
    
    private String keeperHost = "localhost";
    
    private Request latestResponse;
    
    private ObservableList<CommodityDto> availableCommodities = FXCollections.observableArrayList();
    private Property<ObservableList<CommodityDto>> offerTableProperty = new SimpleObjectProperty<>(availableCommodities);

    private ObservableList<CommodityDto> preOrderCommodities = FXCollections.observableArrayList();
    private Property<ObservableList<CommodityDto>> preOrderTableProperty = new SimpleObjectProperty<>(preOrderCommodities);

    
    private Property<ObservableList<CommodityDto>> cartCommoditiesTableProperty;
    
    private ObservableList<CommodityDto> returnCommodities = FXCollections.observableArrayList();
    private Property<ObservableList<CommodityDto>> returnCommoditiesTableProperty = new SimpleObjectProperty<>(returnCommodities);
    
    private Property<ObservableList<ReceiptDto>> receiptsTableProperty;
    
    private ObservableList<CommodityDto> receiptContentCommodities = FXCollections.observableArrayList();
    private Property<ObservableList<CommodityDto>> receiptContentCommoditiesTableProperty = new SimpleObjectProperty<>(receiptContentCommodities);
    

    public void initialize(){
        offerTable.itemsProperty().bind(offerTableProperty);
        preOrderTable.itemsProperty().bind(preOrderTableProperty);
        returnTable.itemsProperty().bind(returnCommoditiesTableProperty);
        receiptContentTable.itemsProperty().bind(receiptContentCommoditiesTableProperty);
        registerButtons.setDisable(false);
        loginButton.setDisable(false);
    }
    
    

    @FXML
    private void onRegisterButtonClick() {
        activeUser = new UserDto(Integer.parseInt(portTextField.getText()), hostTextField.getText(), Role.CLIENT);
        Request request= new Request(KeeperInterfaceActions.REGISTER, activeUser);
        registerOrLogin(request);
        startServer();
    }

    @FXML
    private void onLoginButtonClick(){
        activeUser = new UserDto(Integer.parseInt(portTextField.getText()), hostTextField.getText(), Role.CLIENT, UUID.fromString(activeUserIdTextField.getText()));
        Request request= new Request(KeeperInterfaceActions.LOGIN, activeUser);
        registerOrLogin(request);
        startServer();
        
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
    
    @FXML
    private void onAddToOrderButtonClick(){
        CommodityDto commodityDto = (CommodityDto) offerTable.getSelectionModel().getSelectedItem();
        if (commodityDto != null){
            preOrderCommodities.add(commodityDto);
            availableCommodities.remove(commodityDto);
            System.out.println(preOrderCommodities);
        }
    }
    
    @FXML
    private void onRemoveFromOrderButtonClick(){
        CommodityDto commodityDto = (CommodityDto) preOrderTable.getSelectionModel().getSelectedItem();
        if (commodityDto != null){
            availableCommodities.add(commodityDto);
            preOrderCommodities.remove(commodityDto);
        }
    }
    
    @FXML
    private void onPutOrderButtonClick(){
        List<CommodityDto> commodities = preOrderCommodities.stream().collect(Collectors.toList());
        Order order = new Order( activeUser.getUuid(), commodities);
        Request request = new Request(KeeperInterfaceActions.CUSTOMER_PUT_ORDER, order);
        runLater(() -> {
            try {
                baseClient = new BaseClient(keeperHost, keeperPort);
                latestResponse = baseClient.sendMessage(request);
                if (latestResponse.getData() != null){
                    availableCommodities.clear();
                    preOrderCommodities.clear();
                    availableCommodities.addAll((List<CommodityDto>) latestResponse.getData());
                }
                preOrderCommodities.clear();
                onGetOfferButtonClick();
                System.out.println(latestResponse);
                baseClient.stop();
                baseClient = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    
    @FXML
    private void onAddToCartButtonClick(){
        CommodityDto commodityDto = (CommodityDto) returnTable.getSelectionModel().getSelectedItem();
        if (commodityDto != null){
            DataMiddleman.addCommodity(commodityDto);
            returnCommodities.remove(commodityDto);
        }
    }
    
    @FXML
    private void onRemoveFromCartButtonClick(){
        CommodityDto commodityDto = (CommodityDto) cartTable.getSelectionModel().getSelectedItem();
        if (commodityDto != null){
            returnCommodities.add(commodityDto);
            DataMiddleman.removeCommodity(commodityDto);
        }
    }
    
    @FXML
    private void onAcceptBySellerButtonClick(){
        List<CommodityDto> commodities = DataMiddleman.getCartCommodities().stream().collect(Collectors.toList());
        List<CommodityDto> commoditiesToReturn = returnCommodities.stream().collect(Collectors.toList());
        ReturningOrder order = new ReturningOrder(activeUser.getUuid(), commodities, commoditiesToReturn);
        this.availableSeller = getFreeUserWithRole(Role.SELLER);
        if (this.availableSeller == null){
            System.out.println("Brak dostepnych sprzedawcow");
            return;
        }
        Request request = new Request(SellerInterfaceActions.CUSTOMER_ACCEPT_ORDER, order);
        runLater(() -> {
            try {
                baseClient = new BaseClient(availableSeller.getHost(), availableSeller.getPort());
                latestResponse = baseClient.sendMessage(request);
                if (latestResponse.getData() != null){
                    DataMiddleman.clearCart();
                    returnCommodities.clear();
                    DataMiddleman.addReceipt((ReceiptDto) latestResponse.getData());
                }
                System.out.println(latestResponse);
                baseClient.stop();
                baseClient = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    
    @FXML
    private void onReturnButtonClick(){
        List<CommodityDto> commoditiesToReturn = returnCommodities.stream().collect(Collectors.toList());
        ReturningOrder order = new ReturningOrder(activeUser.getUuid(), null, commoditiesToReturn);
        this.availableDeliverer = getFreeUserWithRole(Role.DELIVERER);
        
        if (this.availableDeliverer == null){
            getFreeUserWithRole(Role.DELIVERER);
            System.out.println("Brak dostepnych dostawcow");
            return;
        }
        Request request = new Request(DelivererInterfaceActions.CUSTOMER_RETURN_ORDER, order);
        runLater(() -> {
            try {
                System.out.println(availableDeliverer);
                baseClient = new BaseClient(availableDeliverer.getHost(), availableDeliverer.getPort());
                latestResponse = baseClient.sendMessage(request);
                if (latestResponse.getData() != null){
                    returnCommodities.clear();
                }
                System.out.println(latestResponse);
                baseClient.stop();
                baseClient = null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void onShowReceiptButton() {
        ReceiptDto receiptDto = (ReceiptDto) receiptTable.getSelectionModel().getSelectedItem();
        if (receiptDto != null){
            receiptContentCommodities.clear();
            receiptContentCommodities.addAll(receiptDto.getCommodities());
        }
    }
    
    @FXML
    private void onReceitTableRefreshButtonClicked(){
        getReceipts();
        DataMiddleman.clearReceipts();
        DataMiddleman.getReceipts().addAll(activeUser.getReceipts());
    }
    
    @FXML
    private void onRefreshCartButtonClick(){
        List<CommodityDto> commodities = DataMiddleman.getCartCommodities().stream().collect(Collectors.toList());
        DataMiddleman.clearCart();
        DataMiddleman.getCartCommodities().addAll(commodities);        
    }

    private void getReceipts() {
        Request request = new Request(KeeperInterfaceActions.CUSTOMER_GET_RECEIPTS, activeUser);
        runLater(() -> {
            try {
                baseClient = new BaseClient(keeperHost, keeperPort);
                latestResponse = baseClient.sendMessage(request);
                if (latestResponse.getData() != null){
                    DataMiddleman.clearReceipts();
                    receiptContentCommodities.clear();
                    DataMiddleman.getReceipts().addAll((List<ReceiptDto>) latestResponse.getData());
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
    
    private void startServer(){
        baseServer = new BaseServer(activeUser.getPort(), activeUser.getHost(), customerServerLogic);
        Platform.runLater(() -> {
            try {
                baseServer.start(false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        cartCommoditiesTableProperty = new SimpleObjectProperty<>(DataMiddleman.getCartCommodities());
        cartTable.itemsProperty().bindBidirectional(cartCommoditiesTableProperty);
        receiptsTableProperty = new SimpleObjectProperty<>(DataMiddleman.getReceipts());
        System.out.println(activeUser.getReceipts());
        DataMiddleman.getReceipts().clear();
        DataMiddleman.getReceipts().addAll(activeUser.getReceipts());
        receiptTable.itemsProperty().bindBidirectional(receiptsTableProperty);
    }
    
    
    private UserDto getFreeUserWithRole(Role role){
        try {
            baseClient = new BaseClient(keeperHost, keeperPort);
            Object[] data = {0, role};
            latestResponse = baseClient.sendMessage(new Request(KeeperInterfaceActions.GET_INFO, data));
            System.out.println(latestResponse);
            if (latestResponse.getData() != null){
                UserDto responseUser = (UserDto) latestResponse.getData();
                if (responseUser.getUuid() != null){
                    if (role.equals(Role.SELLER)){
                        availableSeller = responseUser;
                    } else {
                        availableDeliverer = responseUser;
                    }
                }
            }
            baseClient.stop();
            baseClient = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (UserDto) latestResponse.getData();
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
        receiptTableRefreshButton.setDisable(false);
        refreshCartButton.setDisable(false);
    }

    
}
