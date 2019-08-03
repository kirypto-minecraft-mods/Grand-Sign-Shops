package kirypto.grandsignshops.Repository;

import java.util.Optional;
import java.util.UUID;

import kirypto.grandsignshops.UnclosedShopCommands.UnclosedShopCommand;

public interface UnclosedCommandRepository {
    void save(UnclosedShopCommand unclosedShopCommand);

    Optional<UnclosedShopCommand> retrieveByPlayer(UUID playerUniqueId);

    void clearByPlayer(UUID playerUniqueId);
}
