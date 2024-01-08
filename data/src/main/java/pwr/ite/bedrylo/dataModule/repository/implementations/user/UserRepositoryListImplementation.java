package pwr.ite.bedrylo.dataModule.repository.implementations.user;

import pwr.ite.bedrylo.dataModule.model.data.User;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;
import pwr.ite.bedrylo.dataModule.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserRepositoryListImplementation implements UserRepository {
    
    private static ArrayList<User> users = new ArrayList<>();
    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }
    
    @Override
    public User findByUuid(UUID uuid){
        return users.stream().filter(o->Objects.equals(o.getUuid(), uuid)).findFirst().orElse(null);
    }

    @Override
    public List<User> findByPort(int port) {
        List<User> result = users.stream().filter(o-> Objects.equals(o.getPort(), port)).toList();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    @Override
    public List<User> findByHost(String host) {
        List<User> result = users.stream().filter(o-> Objects.equals(o.getHost(), host)).toList();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    @Override
    public List<User> findByHostAndPort(String host, int port) {
        List<User> result = users.stream().filter(o-> (Objects.equals(o.getHost(), host)&&Objects.equals(o.getPort(), port))).toList();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    @Override
    public List<User> findByRole(Role role) {
        List<User> result = users.stream().filter(o-> Objects.equals(o.getRole(), role)).toList();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    @Override
    public List<User> findByBusyStatus(Boolean busy) {
        List<User> result = users.stream().filter(o-> Objects.equals(o.isBusy(), busy)).toList();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    @Override
    public User updateBusyByUuid(UUID uuid, Boolean busy) {
        User user = users.stream().filter(o->Objects.equals(o.getUuid(), uuid)).findFirst().orElse(null);
        if(user != null)
            user.setBusy(busy);
        return user;
    }

    @Override
    public void delete(UUID uuid) {
        users =(ArrayList<User>) users.stream().filter(o->!o.getUuid().equals(uuid)).toList();
    }
}
