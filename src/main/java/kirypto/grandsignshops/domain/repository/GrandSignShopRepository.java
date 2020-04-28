package kirypto.grandsignshops.domain.repository;

import java.util.Optional;

import kirypto.grandsignshops.domain.BlockLocation;
import kirypto.grandsignshops.domain.GrandSignShop;

public interface GrandSignShopRepository {
    void create(GrandSignShop grandSignShop);

    Optional<GrandSignShop> retrieve(BlockLocation blockLocation);

    void delete(BlockLocation blockLocation);
}
