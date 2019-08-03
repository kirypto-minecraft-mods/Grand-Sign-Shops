package kirypto.grandsignshops.UnclosedShopCommands;

import java.util.Optional;
import java.util.UUID;

import kirypto.grandsignshops.PriceRange;

public class UnclosedCreateShopCommand extends UnclosedShopCommand {
    private final String itemName;
    private final Integer metadataOrNull;
    private final PriceRange buyPrice;
    private final PriceRange sellPrice;
    private final int buyPriceHigh;
    private final int buyPriceLow;
    private final int sellPriceHigh;
    private final int sellPriceLow;

    private UnclosedCreateShopCommand(
            UnclosedShopCommandType unclosedShopCommandType,
            UUID playerUniqueId,
            String itemName,
            Integer metadataOrNull,
            int buyPriceHigh,
            int buyPriceLow,
            int sellPriceHigh,
            int sellPriceLow) {
        super(unclosedShopCommandType, playerUniqueId);
        this.itemName = itemName;
        this.metadataOrNull = metadataOrNull;
        this.buyPrice = PriceRange.of(buyPriceLow, buyPriceHigh);
        this.sellPrice = PriceRange.of(sellPriceLow, sellPriceHigh);
        this.buyPriceHigh = buyPriceHigh;
        this.buyPriceLow = buyPriceLow;
        this.sellPriceHigh = sellPriceHigh;
        this.sellPriceLow = sellPriceLow;
    }

    public static UnclosedCreateShopCommand of(
            UnclosedShopCommandType unclosedShopCommandType,
            UUID playerUniqueId,
            String itemName,
            Integer metadataOrNull,
            int buyPriceHigh,
            int buyPriceLow,
            int sellPriceHigh,
            int sellPriceLow) {
        return new UnclosedCreateShopCommand(
                unclosedShopCommandType,
                playerUniqueId,
                itemName,
                metadataOrNull,
                buyPriceHigh,
                buyPriceLow,
                sellPriceHigh,
                sellPriceLow);
    }

    public String getItemName() {
        return itemName;
    }

    public Optional<Integer> getMetadata() {
        return Optional.ofNullable(metadataOrNull);
    }

    public int getBuyPriceHigh() {
        return buyPriceHigh;
    }

    public int getBuyPriceLow() {
        return buyPriceLow;
    }

    public int getSellPriceHigh() {
        return sellPriceHigh;
    }

    public int getSellPriceLow() {
        return sellPriceLow;
    }

    public PriceRange getBuyPrice() {
        return buyPrice;
    }

    public PriceRange getSellPrice() {
        return sellPrice;
    }
}
