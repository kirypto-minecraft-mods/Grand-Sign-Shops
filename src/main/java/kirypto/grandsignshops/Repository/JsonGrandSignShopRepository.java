package kirypto.grandsignshops.Repository;

import org.apache.commons.lang3.NotImplementedException;

import java.io.File;
import java.util.Optional;

import kirypto.grandsignshops.GrandSignShop;

public class JsonGrandSignShopRepository implements GrandSignShopRepository {
    private final File grandSignShopsRootFolder;

    public JsonGrandSignShopRepository(File grandSignShopsRootFolder) {
        this.grandSignShopsRootFolder = grandSignShopsRootFolder;
    }

    @Override
    public void create(GrandSignShop grandSignShop) {
        throw new NotImplementedException("");
    }

    @Override
    public Optional<GrandSignShop> retrieve(BlockLocation blockLocation) {
        throw new NotImplementedException("");
    }

    @Override
    public void delete(BlockLocation blockLocation) {
        throw new NotImplementedException("");
    }
}
