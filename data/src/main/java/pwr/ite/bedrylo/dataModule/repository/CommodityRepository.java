package pwr.ite.bedrylo.dataModule.repository;

import pwr.ite.bedrylo.dataModule.model.data.Commodity;
import pwr.ite.bedrylo.dataModule.model.data.Receipt;

import java.util.List;
import java.util.UUID;

public interface CommodityRepository {

    Commodity save(Commodity commodity);

    List<Commodity> findAvailable();

    List<Commodity> findByName(String name);

    List<Commodity> findByReceipt(Receipt receipt);

    Commodity findByUuid(UUID uuid);

    Commodity upadteReceiptByUuid(UUID uuid, Receipt receipt);//!remove ?

    Commodity updateInWarehouseByUuid(UUID uuid, boolean inWarehouse);

    void delete(UUID uuid);

}
