package pwr.ite.bedrylo.dataModule.repository;

import pwr.ite.bedrylo.dataModule.model.data.Commodity;

import java.util.List;
import java.util.UUID;

public interface CommodityRepository {

    Commodity save(Commodity commodity);

    List<Commodity> findByName(String name);

    List<Commodity> findByReceiptUuid(UUID receiptUuid);

    Commodity findByUuid(UUID uuid);

    Commodity upadteReceiptUuidByUuid(UUID uuid, UUID receiptUuid);//!remove ?

    void delete(UUID uuid);

}
