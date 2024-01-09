package pwr.ite.bedrylo.dataModule.repository;

import pwr.ite.bedrylo.dataModule.model.data.Receipt;

import java.util.List;
import java.util.UUID;

public interface ReceiptRepository {
    
    Receipt save(Receipt receipt);
    
    Receipt findByUuid(UUID uuid);
    
    List<Receipt> findByUserUuid(UUID userUuid);
    
    void delete(UUID uuid);
    
}
