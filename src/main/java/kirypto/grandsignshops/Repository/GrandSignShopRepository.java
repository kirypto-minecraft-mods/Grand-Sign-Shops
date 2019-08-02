package kirypto.grandsignshops.Repository;

import java.util.Optional;

import kirypto.grandsignshops.GrandSignShop;

public interface GrandSignShopRepository {
    void create(GrandSignShop grandSignShop);

    Optional<GrandSignShop> retrieve(BlockLocation blockLocation);

    void delete(BlockLocation blockLocation);
}
