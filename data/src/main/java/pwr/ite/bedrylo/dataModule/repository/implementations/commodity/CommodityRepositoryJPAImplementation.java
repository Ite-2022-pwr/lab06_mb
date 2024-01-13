package pwr.ite.bedrylo.dataModule.repository.implementations.commodity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pwr.ite.bedrylo.dataModule.model.data.Commodity;
import pwr.ite.bedrylo.dataModule.model.data.Receipt;
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
            if (commodity.getUuid() == null){
                commodity.generateUuid();
            }
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
    public List<Commodity> findAvailable() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Commodity> commodityList = entityManager.createNamedQuery("Commodity.Available", Commodity.class)
                    .getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return commodityList;
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
            entityManager.getTransaction().commit();
            entityManager.close();
            return commodityList;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<Commodity> findByReceipt(Receipt receipt) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Commodity> commodityList = entityManager.createNamedQuery("Commodity.FindByReceipt", Commodity.class)
                    .setParameter("receipt", receipt)
                    .getResultList();
            entityManager.getTransaction().commit();
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
    public Commodity upadteReceiptByUuid(UUID uuid, Receipt receipt) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Commodity commodity = entityManager.createNamedQuery("Commodity.UpdateReceiptByUuid", Commodity.class)
                    .setParameter("uuid", uuid)
                    .setParameter("receipt", receipt).getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return commodity;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public Commodity updateInWarehouseByUuid(UUID uuid, boolean inWarehouse) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("Commodity.UpdateInWarehouseByUuid")
                    .setParameter("uuid", uuid)
                    .setParameter("inWarehouse", inWarehouse).executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
            return findByUuid(uuid);
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
            entityManager.createNamedQuery("Commodity.Delete")
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
