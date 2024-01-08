package pwr.ite.bedrylo.service;

import pwr.ite.bedrylo.model.data.User;

import pwr.ite.bedrylo.dto.UserDto;

public class UserService {
    
    private static UserService instance = null;

    public static UserService getInstance(){
        if (instance == null) {
        instance = new UserService();
        }
        return instance;
    }
    
    public User createUserFromDto(UserDto userDto) {
        User user = new User();
        user.setPort(userDto.getPort());
        user.setHost(userDto.getHost());
        user.setRole(userDto.getRole());
        user.setBusy(userDto.isBusy());
        return user;
    }


    public UserDto createDtoFromUser(User user) {
        return new UserDto(user.getPort(), user.getHost(), user.getRole(), user.isBusy());
    }
}
