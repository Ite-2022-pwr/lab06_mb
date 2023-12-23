package pwr.ite.bedrylo.dto;

import lombok.Data;
import pwr.ite.bedrylo.model.enums.Role;

import java.util.UUID;

@Data
public class UserDto {
    
    private UUID uuid;
    private int port;
    private String host;
    private Role role;
    
    public UserDto(int port, String host, Role role) {
        this.port = port;
        this.host = host;
        this.role = role;
    }
    
}
