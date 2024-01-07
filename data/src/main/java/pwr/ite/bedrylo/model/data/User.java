package pwr.ite.bedrylo.model.data;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pwr.ite.bedrylo.model.data.enums.Role;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {


    @Column(name = "port", nullable = false)
    private int port;

    @Column(name = "host", nullable = false)
    private String host;

    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "busy", nullable = false)
    private boolean busy;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("uuid: ").append(getUuid()).append(", host:").append(getHost()).append(", port:").append(getPort()).append(", role:").append(getRole().toString()).append(", busy:").append(isBusy()).append(" ");
        return stringBuilder.toString();
    }

}
