package pwr.ite.bedrylo.repository.implementations.commodity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pwr.ite.bedrylo.model.data.Commodity;
import pwr.ite.bedrylo.model.data.User;
import pwr.ite.bedrylo.repository.CommodityRepository;

import java.util.List;
import java.util.UUID;

public class CommodityRepositoryJPAImplementation implements CommodityRepository {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("database");

    private EntityManager entityManager = entityManagerFactory.createEntityManager();


    @Override
    public Commodity save(Commodity commodity) {
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(commodity);
            entityManager.getTransaction().commit();
            return commodity;
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<Commodity> findByName(String name) {
        try {
            entityManager.getTransaction().begin();
            return entityManager.createNamedQuery("Commodity.FindByName", Commodity.class)
                    .setParameter("name", name)
                    .getResultList();
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<Commodity> findByUserUuid(UUID userUuid) {
        try {
            entityManager.getTransaction().begin();
            return entityManager.createNamedQuery("Commodity.FindByUserUuid", Commodity.class)
                    .setParameter("userUuid", userUuid)
                    .getResultList();
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public Commodity findByUuid(UUID uuid) {
        try {
            entityManager.getTransaction().begin();
            Commodity commodity = entityManager.find(Commodity.class,uuid);
            entityManager.getTransaction().commit();
            return commodity;
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void delete(UUID uuid) {
        try {
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("Commodity.Delete", Commodity.class)
                    .setParameter("uuid", uuid);
                    
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
