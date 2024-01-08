package pwr.ite.bedrylo.dataModule.dto;

import lombok.Value;
import pwr.ite.bedrylo.dataModule.model.data.User;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
public class UserDto implements Serializable {
    int port;
    String host;
    Role role;
    boolean busy;
}