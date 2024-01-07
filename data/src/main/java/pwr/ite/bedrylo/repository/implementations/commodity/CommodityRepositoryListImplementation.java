package pwr.ite.bedrylo.repository.implementations.commodity;

import pwr.ite.bedrylo.model.data.Commodity;
import pwr.ite.bedrylo.repository.CommodityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CommodityRepositoryListImplementation implements CommodityRepository {
    
    private static List<Commodity> commodities = new ArrayList<>();
    @Override
    public Commodity save(Commodity commodity) {
        commodities.add(commodity);
        return commodity;
    }

    @Override
    public List<Commodity> findByName(String name) {
        List<Commodity> result = commodities.stream().filter(o-> Objects.equals(o.getName(), name)).toList();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }


    @Override
    public Commodity findByUuid(UUID uuid){
        return commodities.stream().filter(o->Objects.equals(o.getUuid(), uuid)).findFirst().orElse(null);
    }

    @Override
    public void delete(UUID uuid) {
        commodities =(ArrayList<Commodity>) commodities.stream().filter(o->!o.getUuid().equals(uuid)).toList();
    }
}
