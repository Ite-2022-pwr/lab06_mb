package pwr.ite.bedrylo.model.data;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
