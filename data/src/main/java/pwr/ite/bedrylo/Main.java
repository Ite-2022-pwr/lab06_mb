package pwr.ite.bedrylo;

import pwr.ite.bedrylo.dto.UserDto;
import pwr.ite.bedrylo.model.data.enums.Role;
import pwr.ite.bedrylo.model.request.Request;
import pwr.ite.bedrylo.model.request.enums.CustomerInterfaceActions;
import pwr.ite.bedrylo.repository.UserRepository;
import pwr.ite.bedrylo.repository.implementations.user.UserRepositoryJPAImplementation;
import pwr.ite.bedrylo.service.UserService;

public class Main {
    public static void main(String[] args) { // ! upewnij się że to wgl o to chodziXD

        UserService userService = new UserService();

        UserRepository userRepository = new UserRepositoryJPAImplementation();
        
        userRepository.save(userService.createUserFromDto(new UserDto(2137,"dupa", Role.DELIVERER, true)));
        
        
        System.out.println();

        Request test = new Request(CustomerInterfaceActions.DELIVERER_PUT_ORDER, null);

        System.out.println(test);
        
        
        System.out.println(userRepository.findByHostAndPort("dupa",2137));
        System.out.println(userRepository.findByHostAndPort("chuj",2137));


    }
}
