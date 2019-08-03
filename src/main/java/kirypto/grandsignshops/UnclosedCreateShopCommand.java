package kirypto.grandsignshops;

import java.util.Map;
import java.util.UUID;

public class UnclosedCreateShopCommand extends UnclosedShopCommand {
    private UnclosedCreateShopCommand(
            UnclosedShopCommandType unclosedShopCommandType,
            UUID playerUniqueId,
            Map<UnclosedCommandParam, Object> params) {
        super(unclosedShopCommandType, playerUniqueId, params);
    }

    public static UnclosedCreateShopCommand of(
            UnclosedShopCommandType unclosedShopCommandType,
            UUID playerUniqueId,
            Map<UnclosedCommandParam, Object> params) {
        return new UnclosedCreateShopCommand(unclosedShopCommandType, playerUniqueId, params);
    }
}
