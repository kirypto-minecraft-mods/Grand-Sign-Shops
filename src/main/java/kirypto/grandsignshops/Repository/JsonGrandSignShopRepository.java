package kirypto.grandsignshops.Repository;

import java.io.File;

public class JsonGrandSignShopRepository implements GrandSignShopRepository {
    private final File grandSignShopsRootFolder;

    public JsonGrandSignShopRepository(File grandSignShopsRootFolder) {

        this.grandSignShopsRootFolder = grandSignShopsRootFolder;
    }
}
