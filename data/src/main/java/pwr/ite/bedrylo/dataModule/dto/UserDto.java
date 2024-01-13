package pwr.ite.bedrylo.dataModule.dto;

import lombok.Value;
import lombok.experimental.Accessors;
import pwr.ite.bedrylo.dataModule.model.data.User;
import pwr.ite.bedrylo.dataModule.model.data.enums.Role;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link User}
 */
@Value
@Accessors(chain = true)
public class UserDto implements Serializable {
    int port;
    String host;
    Role role;
    Boolean busy;
    UUID uuid;
    Set<ReceiptDto> receipts;

    public UserDto(int port, String host, Role role, Boolean busy, UUID uuid) {
        this.port = port;
        this.host = host;
        this.role = role;
        this.busy = busy;
        this.uuid = uuid;
        this.receipts = new HashSet<>();
    }

    public UserDto(int port, String host, Role role, Boolean busy, UUID uuid, Set<ReceiptDto> receipts) {
        this.port = port;
        this.host = host;
        this.role = role;
        this.busy = busy;
        this.uuid = uuid;
        this.receipts = receipts;
    }

    public UserDto(int port, String host, Role role) {
        this.port = port;
        this.host = host;
        this.role = role;
        this.busy = null;
        this.uuid = null;
        this.receipts = new HashSet<>();
    }

    public UserDto(int port, String host, Role role, UUID uuid) {
        this.port = port;
        this.host = host;
        this.role = role;
        this.busy = null;
        this.uuid = uuid;
        this.receipts = new HashSet<>();
    }

}