package pwr.ite.bedrylo.dataModule.model.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "receipts")
public class Receipt extends BaseEntity{
    
    @Column(name = "userUuid")
    private UUID userUuid;
    
    @OneToMany(mappedBy = "receiptUuid")
    private Set<Commodity> commodities;
    
    

}
