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

import kirypto.grandsignshops.Repository.UnclosedCommandRepository;

import static kirypto.grandsignshops.Utilities.sendPlayerMessage;

public class ForgeEventHandlers {
    private final SignEventHandler signEventHandler;

    public ForgeEventHandlers(UnclosedCommandRepository unclosedCommandRepository) {
        signEventHandler = new SignEventHandler(unclosedCommandRepository);
    }

    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (!(event instanceof PlayerInteractEvent.LeftClickBlock) && !(event instanceof PlayerInteractEvent.RightClickBlock)) {
            return;
        }

        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Block block = world.getBlockState(pos).getBlock();
        String clickType = (event instanceof PlayerInteractEvent.LeftClickBlock) ? "left" : "right";


        if (!(block instanceof BlockSign)) {
            sendPlayerMessage(player, "Not a sign block.");
            return;
        }

        TileEntity tileEntity = world.getTileEntity(pos);
        if (!(tileEntity instanceof TileEntitySign)) {
            sendPlayerMessage(player, "Not a sign entity.");
            return;
        }

        TileEntitySign tileEntitySign = (TileEntitySign) tileEntity;

        signEventHandler.handleSignClick(player, tileEntitySign, clickType);
    }
}
