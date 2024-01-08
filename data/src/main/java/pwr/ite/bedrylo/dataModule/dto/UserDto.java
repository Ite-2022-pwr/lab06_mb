package pwr.ite.bedrylo.dataModule.dto;

import lombok.Value;
import pwr.ite.bedrylo.dataModule.model.data.User;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link User}
 */
@Value
public class UserDto implements Serializable {
    int port;
    String host;
    Role role;
    Boolean busy;
    UUID uuid;

    public UserDto(int port, String host, Role role, Boolean busy, UUID uuid) {
        this.port = port;
        this.host = host;
        this.role = role;
        this.busy = busy;
        this.uuid = uuid;
    }

    public UserDto(int port, String host, Role role) {
        this.port = port;
        this.host = host;
        this.role = role;
        this.busy = null;
        this.uuid = null;
    }
}