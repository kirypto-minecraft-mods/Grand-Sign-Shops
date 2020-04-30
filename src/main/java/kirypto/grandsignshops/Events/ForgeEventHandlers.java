// package kirypto.grandsignshops.Events;
//
// import net.minecraftforge.event.entity.player.PlayerInteractEvent;
// import net.minecraftforge.event.world.BlockEvent;
// import net.minecraftforge.event.world.ExplosionEvent;
// import net.minecraftforge.fml.common.eventhandler.EventPriority;
// import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
// import kirypto.grandsignshops.domain.repository.GrandSignShopRepository;
// import kirypto.grandsignshops.domain.repository.UnclosedCommandRepository;
//
// public class ForgeEventHandlers {
//     private final PlayerSignInteractionHandler playerSignInteractionHandler;
//     private final ShopProtectionHandler shopProtectionHandler;
//
//     public ForgeEventHandlers(
//             UnclosedCommandRepository unclosedCommandRepository,
//             GrandSignShopRepository grandSignShopRepository) {
//         this.playerSignInteractionHandler = new PlayerSignInteractionHandler(unclosedCommandRepository, grandSignShopRepository);
//         this.shopProtectionHandler = new ShopProtectionHandler(grandSignShopRepository);
//     }
//
//     @SubscribeEvent(priority = EventPriority.HIGHEST)
//     public void highestPriority_onBlockBreak(BlockEvent.BreakEvent event)
//     {
//         shopProtectionHandler.handleShopProtection(event);
//     }
//
//     @SubscribeEvent(priority = EventPriority.HIGHEST)
//     public void highestPriority_onBlockPlace(BlockEvent.PlaceEvent event) {
//         shopProtectionHandler.handleShopProtection(event);
//     }
//
//     @SubscribeEvent(priority = EventPriority.HIGHEST)
//     public void highestPriority_onDetonation(ExplosionEvent.Detonate event) {
//         shopProtectionHandler.handleShopProtection(event);
//     }
//
//     @SubscribeEvent(priority = EventPriority.HIGHEST)
//     public void highestPriority_onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
//         shopProtectionHandler.handleShopProtection(event);
//     }
//
//     @SubscribeEvent(priority = EventPriority.HIGHEST)
//     public void highestPriority_onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
//         shopProtectionHandler.handleShopProtection(event);
//     }
//
//     @SubscribeEvent(priority = EventPriority.NORMAL)
//     public void normalPriority_onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
//         playerSignInteractionHandler.handleLeftClickBlock(event);
//     }
//
//     @SubscribeEvent(priority = EventPriority.NORMAL)
//     public void normalPriority_onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
//         playerSignInteractionHandler.handleRightClickBlock(event);
//     }
// }
