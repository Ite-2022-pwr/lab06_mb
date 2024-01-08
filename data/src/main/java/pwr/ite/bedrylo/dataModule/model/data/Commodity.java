package pwr.ite.bedrylo.dataModule.model.data;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "commodities")
@NamedQueries({
        @NamedQuery(name = "Commodity.FindByName", query = "select c from Commodity c where c.name = :name"),
        @NamedQuery(name = "Commodity.FindByUserUuid", query = "select c from Commodity c where c.userUuid = :userUuid"),
        @NamedQuery(name = "Commodity.Delete", query = "delete from Commodity c where c.uuid = :uuid"),
        @NamedQuery(name = "Commodity.UpdateUserUuidByUuid", query = "update Commodity c set c.userUuid = :userUuid where c.uuid = :uuid")
})
public class Commodity extends BaseEntity{
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "owner")
    private UUID userUuid;
}
