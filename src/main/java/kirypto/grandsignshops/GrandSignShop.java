package kirypto.grandsignshops;

import java.util.UUID;

public class GrandSignShop {
    private final UUID playerID;
    private final BlockLocation signLocation;
    private final BlockLocation chestLocation;
    private final String itemName;
    private final Integer metadata;
    private final PriceRange buyPrice;
    private final PriceRange sellPrice;

    private GrandSignShop(
            UUID playerID,
            BlockLocation signLocation,
            BlockLocation chestLocation,
            String itemName,
            Integer metadata,
            PriceRange buyPrice,
            PriceRange sellPrice) {
        this.playerID = playerID;
        this.signLocation = signLocation;
        this.chestLocation = chestLocation;
        this.itemName = itemName;
        this.metadata = metadata;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public static GrandSignShop of(
            UUID playerID,
            BlockLocation signLocation,
            BlockLocation chestLocation,
            String itemName,
            PriceRange buyPrice,
            PriceRange sellPrice) {
        return new GrandSignShop(playerID, signLocation, chestLocation, itemName, null, buyPrice, sellPrice);
    }

    public static GrandSignShop of(
            UUID playerID,
            BlockLocation signLocation,
            BlockLocation chestLocation,
            String itemName,
            Integer metadata,
            PriceRange buyPrice,
            PriceRange sellPrice) {
        return new GrandSignShop(playerID, signLocation, chestLocation, itemName, metadata, buyPrice, sellPrice);
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

    public String getItemName() {
        return itemName;
    }

    public Integer getMetadata() {
        return metadata;
    }

    public PriceRange getBuyPrice() {
        return buyPrice;
    }

    public PriceRange getSellPrice() {
        return sellPrice;
    }
}
