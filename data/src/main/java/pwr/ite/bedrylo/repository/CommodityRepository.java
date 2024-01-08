package pwr.ite.bedrylo.repository;

import pwr.ite.bedrylo.model.data.Commodity;

import java.util.List;
import java.util.UUID;

public interface CommodityRepository {
    
    Commodity save(Commodity commodity);
    
    List<Commodity> findByName(String name);

    List<Commodity> findByUserUuid(UUID userUuid);
    
    Commodity findByUuid(UUID uuid);
    
    Commodity upadteUserUuidByUuid(UUID uuid, UUID userUuid);
    
    void delete(UUID uuid);
    
}
