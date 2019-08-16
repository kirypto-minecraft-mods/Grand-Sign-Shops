package kirypto.grandsignshops.Events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import kirypto.grandsignshops.BlockLocation;
import kirypto.grandsignshops.PlayerSignInteractionType;
import kirypto.grandsignshops.Repository.GrandSignShopRepository;
import kirypto.grandsignshops.Repository.UnclosedCommandRepository;
import kirypto.grandsignshops.TextFormatStyle;

import static java.lang.String.format;
import static kirypto.grandsignshops.Utilities.sendPlayerMessage;

public class ForgeEventHandlers {
    private final PlayerSignInteractionHandler playerSignInteractionHandler;
    private final GrandSignShopRepository grandSignShopRepository;

    public ForgeEventHandlers(
            UnclosedCommandRepository unclosedCommandRepository,
            GrandSignShopRepository grandSignShopRepository) {
        this.grandSignShopRepository = grandSignShopRepository;
        this.playerSignInteractionHandler = new PlayerSignInteractionHandler(unclosedCommandRepository, grandSignShopRepository);
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

        playerSignInteractionHandler.handleSignClick(player, tileEntitySign, interactionType);
    }

    // TODO kirypto 2019-Aug-16: Move this to a protected block handler class
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onBlockBreakTEST(BlockEvent.BreakEvent event)
    {
        BlockLocation eventBlockLocation = BlockLocation.of(event.getWorld(), event.getPos());
        if (grandSignShopRepository.retrieve(eventBlockLocation).isPresent()) {
            event.setCanceled(true);
        }
    }

    // TODO kirypto 2019-Aug-16: Move this to a protected block handler class
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onDetonation(ExplosionEvent.Detonate event) {
        int affectedCountBefore = event.getAffectedBlocks().size();
        List<BlockPos> protectedBlocks = new ArrayList<>();

        for (BlockPos affectedBlock : event.getAffectedBlocks()) {
            BlockLocation blockLocation = BlockLocation.of(event.getWorld(), affectedBlock);
            boolean isBlockProtectedByShop = grandSignShopRepository.retrieve(blockLocation).isPresent();
            if (isBlockProtectedByShop) {
                protectedBlocks.add(affectedBlock);
            }
        }

        for (BlockPos protectedBlock : protectedBlocks) {
            event.getAffectedBlocks().remove(protectedBlock);
        }

        if (protectedBlocks.size() > 0) {
            System.out.println(format("~~> Explosion includes shop blocks, filtering out %s blocks. Would have affected %s, now only affects %s.",
                                      protectedBlocks.size(), affectedCountBefore, event.getAffectedBlocks().size()));
        }
    }
}
