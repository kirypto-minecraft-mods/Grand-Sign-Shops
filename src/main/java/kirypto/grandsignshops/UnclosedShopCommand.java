package kirypto.grandsignshops;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class UnclosedShopCommand {
    private final UnclosedShopCommandType unclosedShopCommandType;
    private final UUID playerUniqueId;
    private final Instant creationTime;
    private final Map<UnclosedCommandParam, Object> params;

    private UnclosedShopCommand(
            UnclosedShopCommandType unclosedShopCommandType,
            UUID playerUniqueId,
            Map<UnclosedCommandParam, Object> params) {
        this.unclosedShopCommandType = unclosedShopCommandType;
        this.playerUniqueId = playerUniqueId;
        this.params = params;
        this.creationTime = Instant.now();
    }

    public static UnclosedShopCommand of(
            UnclosedShopCommandType unclosedShopCommandType,
            UUID playerUniqueId,
            Map<UnclosedCommandParam, Object> params) {
        return new UnclosedShopCommand(unclosedShopCommandType, playerUniqueId, params);
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

    public Map<UnclosedCommandParam, Object> getParams() {
        return params;
    }
}
