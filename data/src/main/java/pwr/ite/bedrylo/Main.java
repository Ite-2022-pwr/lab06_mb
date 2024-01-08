package pwr.ite.bedrylo;

import pwr.ite.bedrylo.dto.UserDto;
import pwr.ite.bedrylo.model.data.User;
import pwr.ite.bedrylo.model.data.enums.Role;
import pwr.ite.bedrylo.model.request.Request;
import pwr.ite.bedrylo.model.request.enums.CustomerInterfaceActions;
import pwr.ite.bedrylo.repository.UserRepository;
import pwr.ite.bedrylo.repository.implementations.user.UserRepositoryJPAImplementation;
import pwr.ite.bedrylo.service.UserService;

import java.util.UUID;

public class Main {
    public static void main(String[] args) { // ! upewnij się że to wgl o to chodziXD

        UserService userService = new UserService();

        UserRepository userRepository = new UserRepositoryJPAImplementation();
        
        User testowy = userRepository.save(userService.createUserFromDto(new UserDto(2137,"test", Role.DELIVERER, false)));

        UUID test = testowy.getUuid();
        
        testowy = null;
        
        System.out.println(userRepository.findByUuid(test));
        
        userRepository.updateBusyByUuid(test, true);

        System.out.println(userRepository.findByUuid(test));
        


    }
}
