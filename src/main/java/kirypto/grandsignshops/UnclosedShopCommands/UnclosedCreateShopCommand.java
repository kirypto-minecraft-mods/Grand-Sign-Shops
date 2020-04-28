// package kirypto.grandsignshops.UnclosedShopCommands;
//
// import java.util.Optional;
// import java.util.UUID;
//
// import kirypto.grandsignshops.domain.PriceRange;
//
// public class UnclosedCreateShopCommand extends UnclosedShopCommand {
//     private final String itemName;
//     private final Integer metadataOrNull;
//     private final PriceRange buyPrice;
//     private final PriceRange sellPrice;
//
//     private UnclosedCreateShopCommand(
//             UnclosedShopCommandType unclosedShopCommandType,
//             UUID playerUniqueId,
//             String itemName,
//             Integer metadataOrNull,
//             PriceRange buyPrice,
//             PriceRange sellPrice) {
//         super(unclosedShopCommandType, playerUniqueId);
//         this.itemName = itemName;
//         this.metadataOrNull = metadataOrNull;
//         this.buyPrice = buyPrice;
//         this.sellPrice = sellPrice;
//     }
//
//     public static UnclosedCreateShopCommand of(
//             UnclosedShopCommandType unclosedShopCommandType,
//             UUID playerUniqueId,
//             String itemName,
//             Integer metadataOrNull,
//             PriceRange buyPrice,
//             PriceRange sellPrice) {
//         return new UnclosedCreateShopCommand(
//                 unclosedShopCommandType,
//                 playerUniqueId,
//                 itemName,
//                 metadataOrNull,
//                 buyPrice,
//                 sellPrice);
//     }
//
//     public String getItemName() {
//         return itemName;
//     }
//
//     public Optional<Integer> getMetadata() {
//         return Optional.ofNullable(metadataOrNull);
//     }
//
//     public PriceRange getBuyPrice() {
//         return buyPrice;
//     }
//
//     public PriceRange getSellPrice() {
//         return sellPrice;
//     }
// }
