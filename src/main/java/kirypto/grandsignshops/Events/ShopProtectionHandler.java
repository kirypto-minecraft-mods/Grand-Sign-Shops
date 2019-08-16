package kirypto.grandsignshops.Events;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import kirypto.grandsignshops.BlockLocation;
import kirypto.grandsignshops.GrandSignShop;
import kirypto.grandsignshops.Repository.GrandSignShopRepository;

public class ShopProtectionHandler {
    private final GrandSignShopRepository grandSignShopRepository;

    public ShopProtectionHandler(GrandSignShopRepository grandSignShopRepository) {

        this.grandSignShopRepository = grandSignShopRepository;
    }

    public void handleShopProtection(BlockEvent.BreakEvent event) {
        BlockLocation eventBlockLocation = BlockLocation.of(event.getWorld(), event.getPos());
        if (grandSignShopRepository.retrieve(eventBlockLocation).isPresent()) {
            event.setCanceled(true);
        }
    }

    public void handleShopProtection(ExplosionEvent.Detonate event) {
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
    }

    public void handleShopProtection(BlockEvent.PlaceEvent event) {
        BlockLocation eventBlockLocation = BlockLocation.of(event.getWorld(), event.getPos());
        if (grandSignShopRepository.retrieve(eventBlockLocation).isPresent()) {
            event.setCanceled(true);
        }
    }

    public void handleShopProtection(PlayerInteractEvent.LeftClickBlock event) {
        if (shouldCancelEvent(event)) {
            event.setCanceled(true);
        }
    }

    public void handleShopProtection(PlayerInteractEvent.RightClickBlock event) {
        if (shouldCancelEvent(event)) {
            event.setCanceled(true);
        }
    }

    private boolean shouldCancelEvent(PlayerInteractEvent event) {
        BlockLocation eventBlockLocation = BlockLocation.of(event.getWorld(), event.getPos());
        Optional<GrandSignShop> shopOptional = grandSignShopRepository.retrieve(eventBlockLocation);
        if (!shopOptional.isPresent()) {
            return false;
        }
        UUID shopOwner = shopOptional.get().getPlayerID();
        if (shopOwner.equals(event.getEntityPlayer().getUniqueID())) {
            return false;
        }

        BlockLocation signLocation = shopOptional.get().getSignLocation();
        return !eventBlockLocation.equals(signLocation);
    }
}
