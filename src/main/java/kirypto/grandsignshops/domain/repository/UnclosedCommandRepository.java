package kirypto.grandsignshops.domain.repository;

import java.util.Optional;
import java.util.UUID;

import kirypto.grandsignshops.domain.command.UnclosedShopCommand;

public interface UnclosedCommandRepository {
    void create(UnclosedShopCommand unclosedShopCommand);

    Optional<UnclosedShopCommand> retrieve(UUID playerUniqueId);

    void delete(UUID playerUniqueId);
}
