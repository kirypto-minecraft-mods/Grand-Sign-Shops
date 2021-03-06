package kirypto.grandsignshops.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import kirypto.grandsignshops.UnclosedShopCommands.UnclosedShopCommand;

public class InMemoryUnclosedCommandRepository implements UnclosedCommandRepository {

    private final Map<UUID, UnclosedShopCommand> unclosedShopCommands;

    public InMemoryUnclosedCommandRepository() {
        unclosedShopCommands = new LinkedHashMap<>();
    }

    @Override
    public void create(UnclosedShopCommand unclosedShopCommand) {
        unclosedShopCommands.put(unclosedShopCommand.getPlayerUniqueId(), unclosedShopCommand);
    }

    @Override
    public Optional<UnclosedShopCommand> retrieve(UUID playerUniqueId)
    {
        return Optional.ofNullable(unclosedShopCommands.get(playerUniqueId));
    }

    @Override
    public void delete(UUID playerUniqueId) {
        unclosedShopCommands.remove(playerUniqueId);
    }
}
