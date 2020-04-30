package kirypto.grandsignshops.domain.command;

import java.time.Instant;
import java.util.UUID;

public abstract class UnclosedShopCommand {
    private final UnclosedShopCommandType unclosedShopCommandType;
    private final UUID playerUniqueId;
    private final Instant creationTime;

    protected UnclosedShopCommand(
            UnclosedShopCommandType unclosedShopCommandType,
            UUID playerUniqueId) {
        this.unclosedShopCommandType = unclosedShopCommandType;
        this.playerUniqueId = playerUniqueId;
        this.creationTime = Instant.now();
    }

    public UnclosedShopCommandType getUnclosedShopCommandType() {
        return unclosedShopCommandType;
    }

    public UUID getPlayerUniqueId() {
        return playerUniqueId;
    }

    public Instant getCreationTime() {
        return creationTime;
    }
}
