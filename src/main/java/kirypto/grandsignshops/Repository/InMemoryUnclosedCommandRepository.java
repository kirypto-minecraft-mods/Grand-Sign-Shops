package kirypto.grandsignshops.Repository;

import org.apache.commons.lang3.NotImplementedException;

import java.util.Optional;
import java.util.UUID;

import kirypto.grandsignshops.UnclosedShopCommand;

public class InMemoryUnclosedCommandRepository implements UnclosedCommandRepository {
    public InMemoryUnclosedCommandRepository() { }

    @Override
    public void save(UnclosedShopCommand unclosedShopCommand) {
        throw new NotImplementedException("");
    }

    @Override
    public Optional<UnclosedShopCommand> retrieveByPlayer(UUID playerUniqueId) {
        throw new NotImplementedException("");
    }

    @Override
    public void clearByPlayer(UUID playerUniqueId) {
        throw new NotImplementedException("");
    }
}
