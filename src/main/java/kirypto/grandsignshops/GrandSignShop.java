package kirypto.grandsignshops;

import java.util.UUID;

import kirypto.grandsignshops.Repository.BlockLocation;

public class GrandSignShop {
    private final UUID playerID;
    private final BlockLocation signLocation;
    private final BlockLocation chestLocation;
    private final PriceRange buyPrice;
    private final PriceRange sellPrice;

    private GrandSignShop(UUID playerID, BlockLocation signLocation, BlockLocation chestLocation, PriceRange buyPrice, PriceRange sellPrice) {
        this.playerID = playerID;
        this.signLocation = signLocation;
        this.chestLocation = chestLocation;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public static GrandSignShop of(
            UUID playerID,
            BlockLocation signLocation,
            BlockLocation chestLocation,
            PriceRange buyPrice,
            PriceRange sellPrice) {
        return new GrandSignShop(playerID, signLocation, chestLocation, buyPrice, sellPrice);
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

    public PriceRange getBuyPrice() {
        return buyPrice;
    }

    public PriceRange getSellPrice() {
        return sellPrice;
    }
}
