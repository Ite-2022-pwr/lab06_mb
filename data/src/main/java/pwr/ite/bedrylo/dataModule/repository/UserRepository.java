package pwr.ite.bedrylo.dataModule.repository;

import pwr.ite.bedrylo.dataModule.model.data.User;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User save(User user);

    User findByUuid(UUID uuid);

    List<User> findByPort(int port);

    List<User> findByHost(String host);

    List<User> findByHostAndPort(String host, int port);

    List<User> findByRole(Role role);

    List<User> findByBusyStatus(Boolean busy);

    User updateBusyByUuid(UUID uuid, Boolean busy);
    
    User updateHostAndPortByUuid(UUID uuid, String host, int port);

    void delete(UUID uuid);

}
