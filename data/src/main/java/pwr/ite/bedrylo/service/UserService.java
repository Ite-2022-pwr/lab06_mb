package pwr.ite.bedrylo.service;

import pwr.ite.bedrylo.dto.UserDto;
import pwr.ite.bedrylo.model.User;

public class UserService {
    
    public User createUserFromDto(UserDto userDto) {
        User user = new User();
        user.setPort(userDto.getPort());
        user.setHost(userDto.getHost());
        user.setRole(userDto.getRole());
        return user;
    }
    
}
