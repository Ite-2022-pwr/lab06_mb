package pwr.ite.bedrylo.dataModule.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pwr.ite.bedrylo.dataModule.model.data.Commodity;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Commodity}
 */
@Data
@Accessors(chain = true)
public class CommodityDto implements Serializable {
    private String name;
    private UUID userUuid;
    private UUID uuid;
}