package pwr.ite.bedrylo.dto;

import lombok.Value;
import pwr.ite.bedrylo.model.enums.Role;

import java.io.Serializable;

/**
 * DTO for {@link pwr.ite.bedrylo.model.User}
 */
@Value
public class UserDto implements Serializable {
    int port;
    String host;
    Role role;
    boolean busy;
}