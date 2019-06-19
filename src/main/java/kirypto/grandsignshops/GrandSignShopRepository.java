package kirypto.grandsignshops;

import java.io.File;

public class GrandSignShopRepository implements IGrandSignShopRepository {
    private final File grandSignShopsRootFolder;

    public GrandSignShopRepository(File grandSignShopsRootFolder) {

        this.grandSignShopsRootFolder = grandSignShopsRootFolder;
    }
}
