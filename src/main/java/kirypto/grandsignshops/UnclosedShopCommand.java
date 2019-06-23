package kirypto.grandsignshops;

import java.util.Map;
import java.util.UUID;

import java.time.Instant;

public class UnclosedShopCommand {
    private final UnclosedShopCommandType unclosedShopCommandType;
    private final UUID playerUniqueId;
    private final Instant creationTime;
    private final Map<String, Integer> params;

    private UnclosedShopCommand(
            UnclosedShopCommandType unclosedShopCommandType,
            UUID playerUniqueId,
            Map<String, Integer> params) {
        this.unclosedShopCommandType = unclosedShopCommandType;
        this.playerUniqueId = playerUniqueId;
        this.params = params;
        this.creationTime = Instant.now();
    }

    public static UnclosedShopCommand of(UnclosedShopCommandType unclosedShopCommandType, UUID playerUniqueId, Map<String, Integer> params) {
        return new UnclosedShopCommand(unclosedShopCommandType, playerUniqueId, params);
    }
}

