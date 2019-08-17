package kirypto.grandsignshops.Events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySign;

import org.apache.commons.lang3.NotImplementedException;

import kirypto.grandsignshops.BlockLocation;
import kirypto.grandsignshops.GrandSignShop;
import kirypto.grandsignshops.PlayerSignInteractionType;
import kirypto.grandsignshops.TextFormatStyle;

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
        throw new NotImplementedException("Attempt buy from shop not implemented");
    }

    private static void attemptSellToShop(EntityPlayer player, GrandSignShop grandSignShop) {
        throw new NotImplementedException("Attempt sell to shop not implemented");
    }
}
