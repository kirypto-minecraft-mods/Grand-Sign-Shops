package kirypto.grandsignshops.Events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import org.apache.commons.lang3.NotImplementedException;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

import kirypto.grandsignshops.BlockLocation;
import kirypto.grandsignshops.GrandSignShop;
import kirypto.grandsignshops.PlayerSignInteractionType;
import kirypto.grandsignshops.PriceRange;
import kirypto.grandsignshops.TextFormatStyle;
import the_fireplace.grandeconomy.api.GrandEconomyApi;

import static java.lang.String.format;
import static kirypto.grandsignshops.Utilities.sendPlayerMessage;

public class PlayerShopInteractionHandler {

    public static void handleShopInteraction(
            EntityPlayer player,
            GrandSignShop grandSignShop,
            TileEntitySign tileEntitySign,
            PlayerSignInteractionType interactionType) {
        if (!grandSignShop.getSignLocation().equals(BlockLocation.of(player.getEntityWorld(), tileEntitySign.getPos()))) {
            sendPlayerMessage(player, TextFormatStyle.ERROR, "The queried shop's location does not match event! " +
                    "(This should not occur, if it does, please report this to the mod author)");
            return;
        }

        switch (interactionType) {
            case LEFT_CLICK:
                attemptBuyFromShop(player, grandSignShop);
                break;
            case RIGHT_CLICK:
                attemptSellToShop(player, grandSignShop);
                break;
        }

    }

    private static void attemptBuyFromShop(EntityPlayer player, GrandSignShop grandSignShop) {
        int exchangePrice = calculateCurrentExchangePrice(grandSignShop, grandSignShop.getBuyPrice());
        if (player.isSneaking()) {
            sendPlayerMessage(player, TextFormatStyle.NORMAL, format("Current cost to buy: %s", exchangePrice));
            return;
        }

        sendPlayerMessage(player, TextFormatStyle.TEST, format("Buy price is: %s", exchangePrice));
        sendPlayerMessage(player, TextFormatStyle.ERROR, "NOPE");
        throw new NotImplementedException("Attempt buy from shop not implemented");
    }

    private static void attemptSellToShop(EntityPlayer player, GrandSignShop grandSignShop) {
        int exchangePrice = calculateCurrentExchangePrice(grandSignShop, grandSignShop.getSellPrice());
        if (player.isSneaking()) {
            sendPlayerMessage(player, TextFormatStyle.NORMAL, format("Current payout from sell: %s", exchangePrice));
            return;
        }

        UUID shopOwnerID = grandSignShop.getPlayerID();
        UUID buyerID = player.getUniqueID();
        if (GrandEconomyApi.getBalance(shopOwnerID) < exchangePrice) {
            sendPlayerMessage(player, TextFormatStyle.WARNING, "Owner does not have enough funds.");
            return;
        }

        NonNullList<ItemStack> playerInventory = player.inventory.mainInventory;
        int playerItemCount = countAmountOfShopItemInInventory(playerInventory, grandSignShop);

        if (playerItemCount < 1) {
            sendPlayerMessage(player, TextFormatStyle.WARNING, "You do not have enough items.");
            return;
        }

        sendPlayerMessage(player, TextFormatStyle.TEST, format("Sell price is: %s", exchangePrice));
        sendPlayerMessage(player, TextFormatStyle.ERROR, "NOPE");
        throw new NotImplementedException("Attempt sell to shop not implemented");
    }

    private static int countAmountOfShopItemInInventory(NonNullList<ItemStack> inventory, GrandSignShop grandSignShop) {
        ResourceLocation shopItemResource = new ResourceLocation(grandSignShop.getItemName());

        return inventory.stream()
                .filter(itemStack -> {
                            boolean resourceNameMatches = shopItemResource.equals(itemStack.getItem().getRegistryName());
                            if (!resourceNameMatches) {
                                return false;
                            }
                            return grandSignShop.getMetadata().map(metadata -> metadata == itemStack.getMetadata()).orElse(true);
                        }
                )
                .mapToInt(ItemStack::getCount)
                .sum();
    }

    private static int calculateCurrentExchangePrice(GrandSignShop grandSignShop, PriceRange buyPrice) {
        int exchangePrice;
        if (buyPrice.getLow() == buyPrice.getHigh()) {
            exchangePrice = buyPrice.getHigh();
        } else {
            double percent = calculateItemStoragePercent(grandSignShop);
            exchangePrice = (int) Math.floor((buyPrice.getHigh() - buyPrice.getLow()) * percent) + buyPrice.getLow();
        }
        return exchangePrice;
    }

    private static double calculateItemStoragePercent(GrandSignShop grandSignShop) {
        TileEntityChest tileEntityChest = getTileEntityOfShop(grandSignShop);
        ResourceLocation shopItemResource = new ResourceLocation(grandSignShop.getItemName());
        return IntStream.range(0, tileEntityChest.getSizeInventory())
                .mapToObj(tileEntityChest::getStackInSlot)
                .filter(itemStack -> {
                            if (itemStack.isEmpty()) {
                                return true;
                            }
                            boolean resourceNameMatches = shopItemResource.equals(itemStack.getItem().getRegistryName());
                            if (!resourceNameMatches) {
                                return false;
                            }
                            return grandSignShop.getMetadata().map(metadata -> metadata == itemStack.getMetadata()).orElse(true);
                        }
                )
                .mapToDouble(itemStack -> itemStack.isEmpty() ? 0.0 : itemStack.getCount() * 1.0 / itemStack.getMaxStackSize())
                .average()
                .orElse(0.0);
    }

    private static TileEntityChest getTileEntityOfShop(GrandSignShop grandSignShop) {
        BlockLocation chestLocation = grandSignShop.getChestLocation();
        WorldServer world = DimensionManager.getWorld(chestLocation.getDimension());
        TileEntity tileEntity = world.getTileEntity(new BlockPos(chestLocation.getX(), chestLocation.getY(), chestLocation.getZ()));
        return Objects.requireNonNull((TileEntityChest) tileEntity);
    }
}
