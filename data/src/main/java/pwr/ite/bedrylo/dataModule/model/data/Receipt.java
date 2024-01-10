package pwr.ite.bedrylo.dataModule.model.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "receipts")
@NamedQueries({
        @NamedQuery(name = "Receipt.FindByUserUuid", query = "select r from Receipt r where r.userUuid = :userUuid"),
        @NamedQuery(name = "Receipt.FindByUuid", query = "select r from Receipt r where r.uuid = :uuid"),
        @NamedQuery(name = "Receipt.Delete", query = "delete from Receipt r where r.uuid = :uuid")
})
public class Receipt extends BaseEntity{
    
    @Column(name = "userUuid")
    private UUID userUuid;
    
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "receipt")
    private List<Commodity> commodities;
    
    

}
