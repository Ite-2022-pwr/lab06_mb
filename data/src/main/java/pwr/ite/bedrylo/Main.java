package pwr.ite.bedrylo;

import pwr.ite.bedrylo.dto.UserDto;
import pwr.ite.bedrylo.model.enums.Role;
import pwr.ite.bedrylo.repository.UserRepository;
import pwr.ite.bedrylo.repository.implementations.user.UserRepositoryJPAImplementation;
import pwr.ite.bedrylo.repository.implementations.user.UserRepositoryListImplementation;
import pwr.ite.bedrylo.service.UserService;

public class Main {
    public static void main(String[] args) { // ! upewnij się że to wgl o to chodziXD

        UserService userService = new UserService();

        UserRepository userRepository = new UserRepositoryJPAImplementation();
        
        userRepository.save(userService.createUserFromDto(new UserDto(2137,"dupa", Role.DELIVERER, false)));
        
        System.out.println(userRepository.findByHostAndPort("dupa",2137));
        System.out.println(userRepository.findByHostAndPort("chuj",2137));
        


    }
}
