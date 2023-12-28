package pwr.ite.bedrylo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "commodities")
public class Commodity extends BaseEntity{
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "owner")
    private UUID userUuid;
}
