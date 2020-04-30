package kirypto.grandsignshops.adapter.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import kirypto.grandsignshops.domain.BlockLocation;
import kirypto.grandsignshops.domain.GrandSignShop;
import kirypto.grandsignshops.domain.repository.GrandSignShopRepository;

public class ShopProtectionHandler {
    private final GrandSignShopRepository grandSignShopRepository;

    public ShopProtectionHandler(GrandSignShopRepository grandSignShopRepository) {

        this.grandSignShopRepository = grandSignShopRepository;
    }

    public void handleShopProtection(BlockEvent.BreakEvent event) {
        BlockLocation eventBlockLocation = toBlockLocation(event.getWorld(), event.getPos());
        if (grandSignShopRepository.retrieve(eventBlockLocation).isPresent()) {
            event.setCanceled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    public void handleShopProtection(ExplosionEvent.Detonate event) {
        Set<BlockPos> affectedBlocksProtectedByShops = event.getAffectedBlocks().stream()
                .filter(blockPos -> grandSignShopRepository.retrieve(toBlockLocation(event.getWorld(), blockPos)).isPresent())
                .collect(Collectors.toSet());
        event.getAffectedBlocks().removeAll(affectedBlocksProtectedByShops);
    }

    public void handleShopProtection(BlockEvent.EntityPlaceEvent event) {
        BlockLocation eventBlockLocation = toBlockLocation(event.getWorld(), event.getPos());
        if (grandSignShopRepository.retrieve(eventBlockLocation).isPresent()) {
            event.setCanceled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    public void handleShopProtection(PlayerInteractEvent.LeftClickBlock event) {
        if (shouldCancelPlayerInteractionEvent(event)) {
            event.setCanceled(true);
        }
    }

    public void handleShopProtection(PlayerInteractEvent.RightClickBlock event) {
        if (shouldCancelPlayerInteractionEvent(event)) {
            event.setCanceled(true);
        }
    }

    private boolean shouldCancelPlayerInteractionEvent(PlayerInteractEvent event) {
        BlockLocation eventBlockLocation = toBlockLocation(event.getWorld(), event.getPos());
        Optional<GrandSignShop> shopOptional = grandSignShopRepository.retrieve(eventBlockLocation);
        if (!shopOptional.isPresent()) {
            return false;
        }
        GrandSignShop grandSignShop = shopOptional.get();

        UUID shopOwner = grandSignShop.getPlayerID();
        if (shopOwner.equals(event.getPlayer().getUniqueID())) {
            return false;
        }

        BlockLocation signLocation = grandSignShop.getSignLocation();
        return !eventBlockLocation.equals(signLocation);
    }

    private static BlockLocation toBlockLocation(IWorld world, BlockPos blockPos) {
        return BlockLocation.of(
                world.getDimension().getType().getId(),
                blockPos.getX(),
                blockPos.getY(),
                blockPos.getZ());
    }
}
