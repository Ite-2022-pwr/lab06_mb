package pwr.ite.bedrylo.model.data;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    
    @Id
    @UuidGenerator
    @Column(name = "uuid", unique = true, nullable = false)
    private UUID uuid;
}
