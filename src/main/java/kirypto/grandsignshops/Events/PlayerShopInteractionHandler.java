package kirypto.grandsignshops.Events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import org.apache.commons.lang3.NotImplementedException;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

import kirypto.grandsignshops.BlockLocation;
import kirypto.grandsignshops.GrandSignShop;
import kirypto.grandsignshops.PlayerSignInteractionType;
import kirypto.grandsignshops.PriceRange;
import kirypto.grandsignshops.TextFormatStyle;
import kirypto.grandsignshops.Utilities.ForgeRegistryHelper;
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
        ItemStack itemStackToBeExchanged = getItemStackToBeExchanged(grandSignShop);

        if (GrandEconomyApi.getBalance(shopOwnerID) < exchangePrice) {
            sendPlayerMessage(player, TextFormatStyle.WARNING, "Owner does not have enough funds.");
            return;
        }

        boolean doesPlayerHaveEnoughItemsToSell = doesPlayerHaveEnoughItemsToSell(player, itemStackToBeExchanged);

        if (!doesPlayerHaveEnoughItemsToSell) {
            sendPlayerMessage(player, TextFormatStyle.WARNING, "You do not have enough items to sell.");
            return;
        }

        sendPlayerMessage(player, TextFormatStyle.TEST, format("Sell price is: %s", exchangePrice));
        sendPlayerMessage(player, TextFormatStyle.ERROR, "NOPE");
        throw new NotImplementedException("Attempt sell to shop not implemented");
    }

    private static boolean doesPlayerHaveEnoughItemsToSell(EntityPlayer player, ItemStack itemStackToBeExchanged) {
        PlayerMainInvWrapper playerMainInvWrapper = new PlayerMainInvWrapper(player.inventory);
        int countOfQueriedItemInInventory = getCountOfQueriedItemInInventory(playerMainInvWrapper, itemStackToBeExchanged);
        return countOfQueriedItemInInventory > itemStackToBeExchanged.getCount();
    }

    private static boolean doesShopHaveEnoughItemsForPlayerToBuy(GrandSignShop grandSignShop, ItemStack itemStackToBeExchanged) {
        TileEntityChest shopChestTileEntity = getTileEntityOfShop(grandSignShop);
        InvWrapper shopChestInventoryWrapper = new InvWrapper(shopChestTileEntity);
        int countOfQueriedItemInInventory = getCountOfQueriedItemInInventory(shopChestInventoryWrapper, itemStackToBeExchanged);
        return countOfQueriedItemInInventory > itemStackToBeExchanged.getCount();
    }

    private static boolean doesPlayerHaveEnoughSpaceToBuy(EntityPlayer player, ItemStack itemStackToBeExchanged) {
        PlayerMainInvWrapper playerMainInvWrapper = new PlayerMainInvWrapper(player.inventory);
        int spaceForQueriedItemInInventory = getSpaceForQueriedItemInInventory(playerMainInvWrapper, itemStackToBeExchanged);
        return spaceForQueriedItemInInventory > itemStackToBeExchanged.getCount();
    }

    private static boolean doesShopHaveEnoughSpaceForPlayerToSell(GrandSignShop grandSignShop, ItemStack itemStackToBeExchanged) {
        TileEntityChest shopChestTileEntity = getTileEntityOfShop(grandSignShop);
        InvWrapper shopChestInventoryWrapper = new InvWrapper(shopChestTileEntity);
        int spaceForQueriedItemInInventory = getSpaceForQueriedItemInInventory(shopChestInventoryWrapper, itemStackToBeExchanged);
        return spaceForQueriedItemInInventory > itemStackToBeExchanged.getCount();
    }

    private static int calculateCurrentExchangePrice(GrandSignShop grandSignShop, PriceRange buyPrice) {
        int exchangePrice;
        if (buyPrice.getLow() == buyPrice.getHigh()) {
            exchangePrice = buyPrice.getHigh();
        } else {
            double percent = calcItemFilledPercentageForShop(grandSignShop);
            exchangePrice = (int) Math.floor((buyPrice.getHigh() - buyPrice.getLow()) * percent) + buyPrice.getLow();
        }
        return exchangePrice;
    }

    private static double calcItemFilledPercentageForShop(GrandSignShop grandSignShop) {
        TileEntityChest tileEntityChest = getTileEntityOfShop(grandSignShop);
        ItemStack itemStackToBeExchanged = getItemStackToBeExchanged(grandSignShop);
        return IntStream.range(0, tileEntityChest.getSizeInventory())
                .mapToObj(tileEntityChest::getStackInSlot)
                .filter(itemStack -> itemStack.isEmpty() || itemStack.isItemEqual(itemStackToBeExchanged))
                .mapToDouble(itemStack -> itemStack.isEmpty() ? 0.0 : calcItemStackFillPercentage(itemStack))
                .average()
                .orElse(0.0);
    }

    private static TileEntityChest getTileEntityOfShop(GrandSignShop grandSignShop) {
        BlockLocation chestLocation = grandSignShop.getChestLocation();
        WorldServer world = DimensionManager.getWorld(chestLocation.getDimension());
        TileEntity tileEntity = world.getTileEntity(new BlockPos(chestLocation.getX(), chestLocation.getY(), chestLocation.getZ()));
        return Objects.requireNonNull((TileEntityChest) tileEntity);
    }

    private static ItemStack getItemStackToBeExchanged(GrandSignShop grandSignShop) {
        String itemName = grandSignShop.getItemName();
        Item shopExchangeItem = ForgeRegistryHelper.getItem(itemName);
        return new ItemStack(shopExchangeItem, 1, grandSignShop.getMetadata().orElse(0));
    }

    private static double calcItemStackFillPercentage(ItemStack itemStack) {
        return itemStack.getCount() * 1.0 / itemStack.getMaxStackSize();
    }

    private static int getSpaceForQueriedItemInInventory(IItemHandler inventoryWrapper, ItemStack itemStackToBeExchanged) {
        int maxStackSize = itemStackToBeExchanged.getMaxStackSize();
        return IntStream.range(0, inventoryWrapper.getSlots())
                .mapToObj(inventoryWrapper::getStackInSlot)
                .filter(itemStack -> itemStack.isEmpty() || itemStackToBeExchanged.isItemEqual(itemStack))
                .mapToInt(itemStack -> itemStack.isEmpty() ? maxStackSize : maxStackSize - itemStack.getCount())
                .sum();
    }

    private static int getCountOfQueriedItemInInventory(IItemHandler inventoryWrapper, ItemStack itemStackToBeExchanged) {
        return IntStream.range(0, inventoryWrapper.getSlots())
                .mapToObj(inventoryWrapper::getStackInSlot)
                .filter(itemStackToBeExchanged::isItemEqual)
                .mapToInt(ItemStack::getCount)
                .sum();
    }
}
