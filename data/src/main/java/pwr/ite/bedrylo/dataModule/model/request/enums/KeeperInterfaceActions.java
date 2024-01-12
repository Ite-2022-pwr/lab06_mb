package pwr.ite.bedrylo.dataModule.model.request.enums;

import java.io.Serializable;

public enum KeeperInterfaceActions implements Serializable {
    REGISTER,
    LOGIN,
    UNREGISTER,
    GET_INFO,
    DELIVERER_GET_ORDER,
    DELIVERER_RETURN_ORDER,
    CUSTOMER_GET_OFFER,
    CUSTOMER_PUT_ORDER,
    SELLER_RETURN_ORDER,
    DISCONNECT
}
