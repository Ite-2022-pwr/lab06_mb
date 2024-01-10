package pwr.ite.bedrylo.dataModule.model.data;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "commodities")
@NamedQueries({
        @NamedQuery(name = "Commodity.FindByName", query = "select c from Commodity c where c.name = :name"),
        @NamedQuery(name = "Commodity.FindByReceipt", query = "select c from Commodity c where c.receipt = :receipt"),
        @NamedQuery(name = "Commodity.Delete", query = "delete from Commodity c where c.uuid = :uuid"),
        @NamedQuery(name = "Commodity.FindByReceiptNull", query = "select c from Commodity c where c.receipt is null"),
        @NamedQuery(name = "Commodity.Available", query = "select c from Commodity c where c.inWarehouse = true"),
        @NamedQuery(name = "Commodity.UpdateInWarehouseByUuid", query = "update Commodity c set c.inWarehouse = :inWarehouse where c.uuid = :uuid"),
        @NamedQuery(name = "Commodity.UpdateReceiptByUuid", query = "update Commodity c set c.receipt = :receipt, c.inWarehouse = :inWarehouse where c.uuid = :uuid")
})
public class Commodity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt")
    private Receipt receipt;
    
    @Column(name = "price")
    private double price;
    
    @Column(name = "inWarehouse")
    private boolean inWarehouse;
    
    
}
