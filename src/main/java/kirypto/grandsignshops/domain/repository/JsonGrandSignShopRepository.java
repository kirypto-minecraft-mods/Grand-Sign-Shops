package kirypto.grandsignshops.domain.repository;

import org.apache.commons.lang3.NotImplementedException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import kirypto.grandsignshops.domain.BlockLocation;
import kirypto.grandsignshops.domain.GrandSignShop;

public class JsonGrandSignShopRepository implements GrandSignShopRepository {
    private final File grandSignShopsRootFolder;
    private final Map<BlockLocation, GrandSignShop> signLocationToGrandSignShopLocation;
    private final Map<BlockLocation, BlockLocation> allGrandSignShopLocationsToCorrespondingSignLocation;

    public JsonGrandSignShopRepository(File grandSignShopsRootFolder) {
        this.grandSignShopsRootFolder = grandSignShopsRootFolder;

        signLocationToGrandSignShopLocation = new HashMap<>();
        allGrandSignShopLocationsToCorrespondingSignLocation = new HashMap<>();
    }

    @Override
    public void create(GrandSignShop grandSignShop) {
        BlockLocation signLocation = grandSignShop.getSignLocation();
        signLocationToGrandSignShopLocation.put(signLocation, grandSignShop);

        Stream.concat(Stream.of(grandSignShop.getChestLocation(), grandSignShop.getSignLocation()),
                      grandSignShop.getAdditionalProtectedLocations().stream())
                .forEach(blockLocation -> allGrandSignShopLocationsToCorrespondingSignLocation.put(blockLocation, signLocation));

        // TODO kirypto 2020-Apr-27: Persist to grandSignShopsRootFolder
    }

    @Override
    public Optional<GrandSignShop> retrieve(BlockLocation blockLocation) {
        return Optional.ofNullable(allGrandSignShopLocationsToCorrespondingSignLocation.get(blockLocation))
                .map(signLocationToGrandSignShopLocation::get);
    }

    @Override
    public void delete(BlockLocation blockLocation) {
        throw new NotImplementedException("");
    }
}
