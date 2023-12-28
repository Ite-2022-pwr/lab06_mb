package pwr.ite.bedrylo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link pwr.ite.bedrylo.model.Commodity}
 */
@Data
@Accessors(chain = true)
public class CommodityDto implements Serializable {
    private String name;
    private UUID userUuid;
}