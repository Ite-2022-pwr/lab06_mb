package pwr.ite.bedrylo;

import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import pwr.ite.bedrylo.dto.UserDto;
import pwr.ite.bedrylo.model.User;
import pwr.ite.bedrylo.model.enums.Role;
import pwr.ite.bedrylo.service.UserService;

import java.util.UUID;

public class Main {
  public static void main(String[] args) { // ! upewnij się że to wgl o to chodziXD
    
    UserService userService = new UserService();

    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    
    User user = userService.createUserFromDto(new UserDto(420,"dupa:host", Role.CLIENT));
    Session session = factory.openSession();
    Transaction tx = session.beginTransaction();
    session.persist(user);
    tx.commit();
    session.close();
    
    Session session2 = factory.openSession();
    Transaction tx2 = session2.beginTransaction();
     User user2 = (User) session2.get(User.class,
     UUID.fromString("28a87866-89e3-4c7d-9455-c6c9c1ca611e"));
     System.out.println(user2);
    tx2.commit();
    session2.close();
    
    
  }
}
