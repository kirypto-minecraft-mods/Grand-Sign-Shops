package kirypto.grandsignshops.Repository;

import java.util.Optional;
import java.util.UUID;

import kirypto.grandsignshops.UnclosedShopCommands.UnclosedShopCommand;

public interface UnclosedCommandRepository {
    void create(UnclosedShopCommand unclosedShopCommand);

    Optional<UnclosedShopCommand> retrieve(UUID playerUniqueId);

    void delete(UUID playerUniqueId);
}
