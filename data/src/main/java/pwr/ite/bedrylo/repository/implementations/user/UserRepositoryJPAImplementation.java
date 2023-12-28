package pwr.ite.bedrylo.repository.implementations.user;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pwr.ite.bedrylo.model.User;
import pwr.ite.bedrylo.model.enums.Role;
import pwr.ite.bedrylo.repository.UserRepository;
import pwr.ite.bedrylo.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class UserRepositoryJPAImplementation implements UserRepository {

    private UserService userService = UserService.getInstance();
    
    private SessionFactory factory = new Configuration().configure().buildSessionFactory();
    
    @Override
    public User save(User user) {
        try {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            session.close();
            return user;
        } catch (Exception e) {
            System.out.println("dupa");
            return null;
        }
    }


    @Override
    public User findByUuid(UUID uuid){
        try {
            Session session = factory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("uuid"), uuid));
            Query query = session.createQuery(criteriaQuery);
            List<User> userList = query.getResultList();
            session.close();
            return userList.get(0);
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<User> findByPort(int port) {
        try {
            Session session = factory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("port"), port));
            Query query = session.createQuery(criteriaQuery);
            List<User> userList = query.getResultList();
            session.close();
            return userList;
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<User> findByHost(String host) {
        try {
            Session session = factory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.like(root.get("host"), host));
            Query query = session.createQuery(criteriaQuery);
            List<User> userList = query.getResultList();
            session.close();
            return userList;
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }
    
    @Override
    public List<User> findByHostAndPort(String host, int port){
        try {
            Session session = factory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            Predicate portPredicate = criteriaBuilder.equal(root.get("port"), port);
            Predicate hostPredicate = criteriaBuilder.like(root.get("host"), host);
            criteriaQuery.select(root).where(criteriaBuilder.and(portPredicate, hostPredicate));
            Query query = session.createQuery(criteriaQuery);
            List<User> userList = query.getResultList();
            session.close();
            return userList;
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<User> findByRole(Role role) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }
}
