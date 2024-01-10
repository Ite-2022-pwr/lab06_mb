package pwr.ite.bedrylo.dataModule.repository.implementations.receipt;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pwr.ite.bedrylo.dataModule.model.data.BaseEntity;
import pwr.ite.bedrylo.dataModule.model.data.Commodity;
import pwr.ite.bedrylo.dataModule.model.data.Receipt;
import pwr.ite.bedrylo.dataModule.model.data.User;
import pwr.ite.bedrylo.dataModule.repository.ReceiptRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReceiptRepositoryJPAImplementation implements ReceiptRepository {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("database");
    
   
    @Override
    public Receipt save(Receipt receipt) {
        if (receipt.getUuid() == null){
            receipt.generateUuid();
        }
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(receipt);
            entityManager.getTransaction().commit();
            for(Commodity commodity: receipt.getCommodities()){
                entityManager.getTransaction().begin();
                entityManager.createNamedQuery("Commodity.UpdateReceiptByUuid")
                        .setParameter("receipt", receipt)
                        .setParameter("uuid", commodity.getUuid())
                        .setParameter("inWarehouse", false)
                        .executeUpdate();
                entityManager.getTransaction().commit();
            }
            entityManager.close();
            return receipt;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public Receipt findByUuid(UUID uuid) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            Receipt receipt = entityManager.find(Receipt.class, uuid);
            entityManager.getTransaction().commit();
            entityManager.close();
            return receipt;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<Receipt> findByUserUuid(UUID userUuid) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Receipt> commodityList = entityManager.createNamedQuery("Receipt.FindByUserUuid", Receipt.class)
                    .setParameter("userUuid", userUuid)
                    .getResultList();
            entityManager.close();
            return commodityList;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void delete(UUID uuid) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("Receipt.Delete")
                    .setParameter("uuid", uuid)
                    .executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
