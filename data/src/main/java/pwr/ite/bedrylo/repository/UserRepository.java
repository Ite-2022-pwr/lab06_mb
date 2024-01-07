package pwr.ite.bedrylo.repository;

import pwr.ite.bedrylo.model.data.User;
import pwr.ite.bedrylo.model.data.enums.Role;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    
    User findByUuid(UUID uuid);
    
    List<User> findByPort(int port);

    List<User> findByHost(String host);

    List<User> findByHostAndPort(String host, int port);

    List<User> findByRole(Role role);
    
    void delete(UUID uuid);
    
}
