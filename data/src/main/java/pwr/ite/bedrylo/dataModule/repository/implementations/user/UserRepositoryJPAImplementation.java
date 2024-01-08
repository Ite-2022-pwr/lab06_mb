package pwr.ite.bedrylo.dataModule.repository.implementations.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pwr.ite.bedrylo.dataModule.model.data.User;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;
import pwr.ite.bedrylo.dataModule.repository.UserRepository;

import java.util.List;
import java.util.UUID;


public class UserRepositoryJPAImplementation implements UserRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("database");
    
    //private EntityManager entityManager = entityManagerFactory.createEntityManager();
    // TODO refactor this shit
    
    @Override
    public User save(User user) {
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            entityManager.close();
            return user;
        }catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;

    }


    @Override
    public User findByUuid(UUID uuid){
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            User user = entityManager.find(User.class,uuid);
            entityManager.getTransaction().commit();
            entityManager.close();
            return user;
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<User> findByPort(int port) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<User> userList = entityManager.createNamedQuery("User.FindByPort", User.class)
                    .setParameter("port", port)
                    .getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return userList;
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<User> findByHost(String host) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<User> userList = entityManager.createNamedQuery("User.FindByHost", User.class)
                    .setParameter("host", host)
                    .getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return userList;
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }
    
    @Override
    public List<User> findByHostAndPort(String host, int port){
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<User> userList = entityManager.createNamedQuery("User.FindByHostAndPort", User.class)
                    .setParameter("host", host)
                    .setParameter("port", port)
                    .getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return userList;
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<User> findByRole(Role role) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<User> userList = entityManager.createNamedQuery("User.FindByRole", User.class)
                    .setParameter("role", role)
                    .getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return userList;
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }
    
    @Override
    public List<User> findByBusyStatus(Boolean busy){
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<User> userList = entityManager.createNamedQuery("User.FindByBusy", User.class)
                    .setParameter("busy", busy)
                    .getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return userList;
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public User updateBusyByUuid(UUID uuid, Boolean busy) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("User.UpdateBusyByUuid")
                    .setParameter("busy", busy)
                    .setParameter("uuid", uuid)
                    .executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        try{
            return findByUuid(uuid);
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void delete(UUID uuid) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("User.Delete", User.class)
                    .setParameter("uuid", uuid);
            entityManager.close();
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
