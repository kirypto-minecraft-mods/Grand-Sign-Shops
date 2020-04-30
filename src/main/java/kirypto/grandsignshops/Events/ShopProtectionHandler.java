package kirypto.grandsignshops.Events;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;

import org.apache.commons.lang3.NotImplementedException;

import kirypto.grandsignshops.domain.repository.GrandSignShopRepository;

public class ShopProtectionHandler {
    private final GrandSignShopRepository grandSignShopRepository;

    public ShopProtectionHandler(GrandSignShopRepository grandSignShopRepository) {

        this.grandSignShopRepository = grandSignShopRepository;
    }

    public void handleShopProtection(BlockEvent.BreakEvent event) {
        throw new NotImplementedException("Not Yet Implemented");
        // BlockLocation eventBlockLocation = BlockLocation.of(event.getWorld(), event.getPos());
        // if (grandSignShopRepository.retrieve(eventBlockLocation).isPresent()) {
        //     event.setCanceled(true);
        // }
    }

    public void handleShopProtection(ExplosionEvent.Detonate event) {
        throw new NotImplementedException("Not Yet Implemented");
        // List<BlockPos> protectedBlocks = new ArrayList<>();
        //
        // for (BlockPos affectedBlock : event.getAffectedBlocks()) {
        //     BlockLocation blockLocation = BlockLocation.of(event.getWorld(), affectedBlock);
        //     boolean isBlockProtectedByShop = grandSignShopRepository.retrieve(blockLocation).isPresent();
        //     if (isBlockProtectedByShop) {
        //         protectedBlocks.add(affectedBlock);
        //     }
        // }
        //
        // for (BlockPos protectedBlock : protectedBlocks) {
        //     event.getAffectedBlocks().remove(protectedBlock);
        // }
    }

    public void handleShopProtection(BlockEvent.EntityPlaceEvent event) {
        throw new NotImplementedException("Not Yet Implemented");
        // BlockLocation eventBlockLocation = BlockLocation.of(event.getWorld(), event.getPos());
        // if (grandSignShopRepository.retrieve(eventBlockLocation).isPresent()) {
        //     event.setCanceled(true);
        // }
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
        throw new NotImplementedException("Not Yet Implemented");
        // BlockLocation eventBlockLocation = BlockLocation.of(event.getWorld(), event.getPos());
        // Optional<GrandSignShop> shopOptional = grandSignShopRepository.retrieve(eventBlockLocation);
        // if (!shopOptional.isPresent()) {
        //     return false;
        // }
        // UUID shopOwner = shopOptional.get().getPlayerID();
        // if (shopOwner.equals(event.getEntityPlayer().getUniqueID())) {
        //     return false;
        // }
        //
        // BlockLocation signLocation = shopOptional.get().getSignLocation();
        // return !eventBlockLocation.equals(signLocation);
    }
}
