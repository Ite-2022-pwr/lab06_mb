package pwr.ite.bedrylo.dataModule.repository.implementations.commodity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pwr.ite.bedrylo.dataModule.model.data.Commodity;
import pwr.ite.bedrylo.dataModule.repository.CommodityRepository;

import java.util.List;
import java.util.UUID;

public class CommodityRepositoryJPAImplementation implements CommodityRepository {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("database");


    @Override
    public Commodity save(Commodity commodity) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(commodity);
            entityManager.getTransaction().commit();
            entityManager.close();
            return commodity;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<Commodity> findByName(String name) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Commodity> commodityList = entityManager.createNamedQuery("Commodity.FindByName", Commodity.class)
                    .setParameter("name", name)
                    .getResultList();
            entityManager.close();
            return commodityList;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<Commodity> findByReceiptUuid(UUID receiptUuid) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Commodity> commodityList = entityManager.createNamedQuery("Commodity.FindByReceiptUuid", Commodity.class)
                    .setParameter("userUuid", receiptUuid)
                    .getResultList();
            entityManager.close();
            return commodityList;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public Commodity findByUuid(UUID uuid) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Commodity commodity = entityManager.find(Commodity.class, uuid);
            entityManager.getTransaction().commit();
            entityManager.close();
            return commodity;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public Commodity upadteReceiptUuidByUuid(UUID uuid, UUID receiptUuid) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Commodity commodity = entityManager.createNamedQuery("Commodity.UpdateReceiptUuidByUuid", Commodity.class)
                    .setParameter("uuid", uuid)
                    .setParameter("userUuid", receiptUuid).getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return commodity;
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
            entityManager.createNamedQuery("Commodity.Delete", Commodity.class)
                    .setParameter("uuid", uuid);
            entityManager.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
