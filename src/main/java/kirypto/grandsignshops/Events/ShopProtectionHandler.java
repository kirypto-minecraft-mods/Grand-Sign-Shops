package kirypto.grandsignshops.Events;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;

import java.util.ArrayList;
import java.util.List;

import kirypto.grandsignshops.BlockLocation;
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
}
