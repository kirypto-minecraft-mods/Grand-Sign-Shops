package kirypto.grandsignshops.Events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import kirypto.grandsignshops.PlayerSignInteractionType;
import kirypto.grandsignshops.Repository.GrandSignShopRepository;
import kirypto.grandsignshops.Repository.UnclosedCommandRepository;
import kirypto.grandsignshops.TextFormatStyle;

import static kirypto.grandsignshops.Utilities.sendPlayerMessage;

public class ForgeEventHandlers {
    private final SignEventHandler signEventHandler;

    public ForgeEventHandlers(
            UnclosedCommandRepository unclosedCommandRepository,
            GrandSignShopRepository grandSignShopRepository) {
        this.signEventHandler = new SignEventHandler(unclosedCommandRepository, grandSignShopRepository);
    }

    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        PlayerSignInteractionType interactionType;
        if ((event instanceof PlayerInteractEvent.LeftClickBlock)) {
            interactionType = PlayerSignInteractionType.LEFT_CLICK;
        } else if ((event instanceof PlayerInteractEvent.RightClickBlock)) {
            interactionType = PlayerSignInteractionType.RIGHT_CLICK;
        } else {
            return;
        }

        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Block block = world.getBlockState(pos).getBlock();

        if (!(block instanceof BlockSign)) {
            sendPlayerMessage(player, TextFormatStyle.TEST, "Not a sign block.");
            return;
        }

        TileEntity tileEntity = world.getTileEntity(pos);
        if (!(tileEntity instanceof TileEntitySign)) {
            sendPlayerMessage(player, TextFormatStyle.TEST, "Not a sign entity.");
            return;
        }

        TileEntitySign tileEntitySign = (TileEntitySign) tileEntity;

        signEventHandler.handleSignClick(player, tileEntitySign, interactionType);
    }
}
