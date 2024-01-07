package pwr.ite.bedrylo.dto;

import lombok.Value;
import pwr.ite.bedrylo.model.data.User;
import pwr.ite.bedrylo.model.data.enums.Role;

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