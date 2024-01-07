package pwr.ite.bedrylo.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pwr.ite.bedrylo.model.data.Commodity;

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
}