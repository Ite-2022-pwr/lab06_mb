package pwr.ite.bedrylo.repository.implementations.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pwr.ite.bedrylo.model.data.User;
import pwr.ite.bedrylo.model.data.enums.Role;
import pwr.ite.bedrylo.repository.UserRepository;

import java.util.List;
import java.util.UUID;


public class UserRepositoryJPAImplementation implements UserRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("database");
    
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    @Override
    public User save(User user) {
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return user;
        }catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;

    }


    @Override
    public User findByUuid(UUID uuid){
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class,uuid);
            entityManager.getTransaction().commit();
            return user;
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<User> findByPort(int port) {
        try {
            entityManager.getTransaction().begin();
            return entityManager.createNamedQuery("User.FindByPort", User.class)
                    .setParameter("port", port)
                    .getResultList();
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<User> findByHost(String host) {
        try {
            entityManager.getTransaction().begin();
            return entityManager.createNamedQuery("User.FindByHost", User.class)
                    .setParameter("host", host)
                    .getResultList();
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }
    
    @Override
    public List<User> findByHostAndPort(String host, int port){
        try {
            entityManager.getTransaction().begin();
            return entityManager.createNamedQuery("User.FindByHostAndPort", User.class)
                    .setParameter("host", host)
                    .setParameter("port", port)
                    .getResultList();
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<User> findByRole(Role role) {
        try {
            entityManager.getTransaction().begin();
            return entityManager.createNamedQuery("User.FindByRole", User.class)
                    .setParameter("role", role)
                    .getResultList();
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }
    
    @Override
    public List<User> findByBusyStatus(Boolean busy){
        try {
            entityManager.getTransaction().begin();
            return entityManager.createNamedQuery("User.FindByBusy", User.class)
                    .setParameter("busy", busy)
                    .getResultList();
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void delete(UUID uuid) {
        try {
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("User.Delete", User.class)
                    .setParameter("uuid", uuid);
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
