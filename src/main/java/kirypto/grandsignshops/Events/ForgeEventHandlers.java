package kirypto.grandsignshops.Events;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import kirypto.grandsignshops.Repository.GrandSignShopRepository;
import kirypto.grandsignshops.Repository.UnclosedCommandRepository;

public class ForgeEventHandlers {
    private final PlayerSignInteractionHandler playerSignInteractionHandler;
    private final ShopProtectionHandler shopProtectionHandler;

    public ForgeEventHandlers(
            UnclosedCommandRepository unclosedCommandRepository,
            GrandSignShopRepository grandSignShopRepository) {
        this.playerSignInteractionHandler = new PlayerSignInteractionHandler(unclosedCommandRepository, grandSignShopRepository);
        this.shopProtectionHandler = new ShopProtectionHandler(grandSignShopRepository);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void highestPriority_onBlockBreak(BlockEvent.BreakEvent event)
    {
        shopProtectionHandler.handleShopProtection(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void highestPriority_onBlockPlace(BlockEvent.PlaceEvent event) {
        shopProtectionHandler.handleShopProtection(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void highestPriority_onDetonation(ExplosionEvent.Detonate event) {
        shopProtectionHandler.handleShopProtection(event);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void normalPriority_onPlayerInteractEvent(PlayerInteractEvent event) {
        playerSignInteractionHandler.handlePlayerSignInteraction(event);
    }
}
