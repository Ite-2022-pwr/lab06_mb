package pwr.ite.bedrylo.repository;

import pwr.ite.bedrylo.model.User;
import pwr.ite.bedrylo.model.enums.Role;

public interface UserRepository {
    User save(User user);
    
    User findByPort(int port);
    
    User findByHost(String host);
    
    User findByRole(Role role);
    
    void delete(User user);
    
}
