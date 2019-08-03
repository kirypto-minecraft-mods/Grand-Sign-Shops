package kirypto.grandsignshops;

import java.util.UUID;

import kirypto.grandsignshops.Repository.BlockLocation;

public class GrandSignShop {
    private final UUID playerID;
    private final BlockLocation signLocation;
    private final BlockLocation chestLocation;

    private GrandSignShop(UUID playerID, BlockLocation signLocation, BlockLocation chestLocation) {
        this.playerID = playerID;
        this.signLocation = signLocation;
        this.chestLocation = chestLocation;
    }

    public static GrandSignShop of(
            UUID playerID,
            BlockLocation signLocation,
            BlockLocation chestLocation) {
        return new GrandSignShop(playerID, signLocation, chestLocation);
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public BlockLocation getSignLocation() {
        return signLocation;
    }

    public BlockLocation getChestLocation() {
        return chestLocation;
    }
}
