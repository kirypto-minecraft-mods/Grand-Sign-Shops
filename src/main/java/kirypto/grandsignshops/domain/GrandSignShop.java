package kirypto.grandsignshops.domain;

public class GrandSignShop {
    // private final UUID playerID;
    // private final BlockLocation signLocation;
    // private final BlockLocation chestLocation;
    // private final String itemName;
    // private final Integer metadata;
    // private final PriceRange buyPrice;
    // private final PriceRange sellPrice;
    // private final List<BlockLocation> additionalProtectedLocations;
    //
    // private GrandSignShop(
    //         UUID playerID,
    //         BlockLocation signLocation,
    //         BlockLocation chestLocation,
    //         String itemName,
    //         Integer metadata,
    //         PriceRange buyPrice,
    //         PriceRange sellPrice) {
    //     this.playerID = playerID;
    //     this.signLocation = signLocation;
    //     this.chestLocation = chestLocation;
    //     this.itemName = itemName;
    //     this.metadata = metadata;
    //     this.buyPrice = buyPrice;
    //     this.sellPrice = sellPrice;
    //     this.additionalProtectedLocations = Stream.of(
    //             BlockLocation.shift(signLocation, 1, 0, 0),
    //             BlockLocation.shift(signLocation, -1, 0, 0),
    //             BlockLocation.shift(signLocation, 0, 0, 1),
    //             BlockLocation.shift(signLocation, 0, 0, -1),
    //             BlockLocation.shift(chestLocation, 1, 0, 0),
    //             BlockLocation.shift(chestLocation, -1, 0, 0),
    //             BlockLocation.shift(chestLocation, 0, 0, 1),
    //             BlockLocation.shift(chestLocation, 0, 0, -1),
    //             BlockLocation.shift(chestLocation, 0, -1, 0)
    //     ).collect(Collectors.toList());
    // }
    //
    // public static GrandSignShop of(
    //         UUID playerID,
    //         BlockLocation signLocation,
    //         BlockLocation chestLocation,
    //         String itemName,
    //         PriceRange buyPrice,
    //         PriceRange sellPrice) {
    //     return new GrandSignShop(playerID, signLocation, chestLocation, itemName, null, buyPrice, sellPrice);
    // }
    //
    // public static GrandSignShop of(
    //         UUID playerID,
    //         BlockLocation signLocation,
    //         BlockLocation chestLocation,
    //         String itemName,
    //         Integer metadata,
    //         PriceRange buyPrice,
    //         PriceRange sellPrice) {
    //     return new GrandSignShop(playerID, signLocation, chestLocation, itemName, metadata, buyPrice, sellPrice);
    // }
    //
    // public UUID getPlayerID() {
    //     return playerID;
    // }
    //
    // public BlockLocation getSignLocation() {
    //     return signLocation;
    // }
    //
    // public BlockLocation getChestLocation() {
    //     return chestLocation;
    // }
    //
    // public String getItemName() {
    //     return itemName;
    // }
    //
    // public Optional<Integer> getMetadata() {
    //     return Optional.ofNullable(metadata);
    // }
    //
    // public PriceRange getBuyPrice() {
    //     return buyPrice;
    // }
    //
    // public PriceRange getSellPrice() {
    //     return sellPrice;
    // }
    //
    // public List<BlockLocation> getAdditionalProtectedLocations() {
    //     return additionalProtectedLocations;
    // }
}
