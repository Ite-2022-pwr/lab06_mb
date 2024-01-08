package pwr.ite.bedrylo.dataModule.service;

import pwr.ite.bedrylo.dataModule.model.data.User;

import pwr.ite.bedrylo.dataModule.dto.UserDto;

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
        if (null != userDto.getBusy()){
            user.setBusy(userDto.getBusy());
        } else {
            user.setBusy(false);
        }
        if (userDto.getUuid()!=null) {
            user.setUuid(userDto.getUuid());
        }
        return user;
    }


    public UserDto createDtoFromUser(User user) {
        return new UserDto(user.getPort(), user.getHost(), user.getRole(), user.isBusy(), user.getUuid());
    }
}
