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
        @NamedQuery(name = "Commodity.FindByReceiptUuid", query = "select c from Commodity c where c.receiptUuid = :userUuid"),
        @NamedQuery(name = "Commodity.Delete", query = "delete from Commodity c where c.uuid = :uuid"),
        @NamedQuery(name = "Commodity.UpdateReceiptUuidByUuid", query = "update Commodity c set c.receiptUuid = :userUuid where c.uuid = :uuid")
})
public class Commodity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "receipt")
    private UUID receiptUuid;
    
    @Column(name = "price")
    private double price;
}
