package pwr.ite.bedrylo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import pwr.ite.bedrylo.model.enums.Role;

import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, nullable = false)
    private UUID uuid;
    
    @Column(name = "port", nullable = false)
    private int port;
    
    @Column(name = "host", nullable = false)
    private String host;
    
    @Column(name = "role", nullable = false)
    private Role role;
    
}
