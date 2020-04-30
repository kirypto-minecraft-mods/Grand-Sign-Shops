// package kirypto.grandsignshops.Events;
//
// import net.minecraft.block.Block;
// import net.minecraft.block.BlockChest;
// import net.minecraft.block.BlockContainer;
// import net.minecraft.block.BlockSign;
// import net.minecraft.block.BlockWallSign;
// import net.minecraft.entity.player.EntityPlayer;
// import net.minecraft.tileentity.TileEntity;
// import net.minecraft.tileentity.TileEntitySign;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.util.text.ITextComponent;
// import net.minecraft.world.World;
// import net.minecraftforge.event.entity.player.PlayerInteractEvent;
//
// import java.time.Duration;
// import java.time.Instant;
// import java.util.Arrays;
// import java.util.Optional;
// import java.util.stream.Stream;
//
// import kirypto.grandsignshops.domain.BlockLocation;
// import kirypto.grandsignshops.domain.GrandSignShop;
// import kirypto.grandsignshops.PlayerSignInteractionType;
// import kirypto.grandsignshops.domain.PriceRange;
// import kirypto.grandsignshops.domain.repository.GrandSignShopRepository;
// import kirypto.grandsignshops.Repository.UnclosedCommandRepository;
// import kirypto.grandsignshops.TextFormatStyle;
// import kirypto.grandsignshops.domain.command.UnclosedCreateShopCommand;
// import kirypto.grandsignshops.domain.command.UnclosedShopCommand;
//
// import static java.lang.String.format;
// import static kirypto.grandsignshops.Utilities.sendPlayerMessage;
// import static kirypto.grandsignshops.Utilities.text;
//
// public class PlayerSignInteractionHandler {
//     private static final int UNCLOSED_COMMAND_MAXIMUM_DURATION = 2;
//
//     private final UnclosedCommandRepository unclosedCommandRepository;
//     private final GrandSignShopRepository grandSignShopRepository;
//
//     public PlayerSignInteractionHandler(
//             UnclosedCommandRepository unclosedCommandRepository,
//             GrandSignShopRepository grandSignShopRepository) {
//         this.unclosedCommandRepository = unclosedCommandRepository;
//         this.grandSignShopRepository = grandSignShopRepository;
//     }
//
//     public void handleLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
//         handlePlayerInteraction(event, PlayerSignInteractionType.LEFT_CLICK);
//     }
//
//     public void handleRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
//         handlePlayerInteraction(event, PlayerSignInteractionType.RIGHT_CLICK);
//     }
//
//     private void handlePlayerInteraction(PlayerInteractEvent event, PlayerSignInteractionType interactionType) {
//         EntityPlayer player = event.getEntityPlayer();
//         World world = event.getWorld();
//         BlockPos blockPos = event.getPos();
//         Block block = world.getBlockState(blockPos).getBlock();
//
//         if (!(block instanceof BlockSign)) {
//             sendPlayerMessage(player, TextFormatStyle.TEST, "Not a sign block.");
//             return;
//         }
//
//         TileEntity tileEntity = world.getTileEntity(blockPos);
//         if (!(tileEntity instanceof TileEntitySign)) {
//             sendPlayerMessage(player, TextFormatStyle.TEST, "Not a sign entity.");
//             return;
//         }
//
//         TileEntitySign tileEntitySign = (TileEntitySign) tileEntity;
//
//         Optional<UnclosedShopCommand> unclosedShopCommandOptional = unclosedCommandRepository.retrieve(player.getUniqueID());
//         if (unclosedShopCommandOptional.isPresent()) {
//             UnclosedShopCommand unclosedShopCommand = unclosedShopCommandOptional.get();
//             handleSignInteractionWithUnclosedShopCommand(player, unclosedShopCommand, tileEntitySign, interactionType);
//             return;
//         }
//
//         Optional<GrandSignShop> grandSignShopOptional = grandSignShopRepository.retrieve(BlockLocation.of(world, blockPos));
//         if (grandSignShopOptional.isPresent()) {
//             PlayerShopInteractionHandler.handleShopInteraction(player, grandSignShopOptional.get(), tileEntitySign, interactionType);
//             //noinspection UnnecessaryReturnStatement
//             return;
//         }
//     }
//
//     private void handleSignInteractionWithUnclosedShopCommand(
//             EntityPlayer player,
//             UnclosedShopCommand unclosedShopCommand,
//             TileEntitySign tileEntitySign,
//             PlayerSignInteractionType signInteractionType) {
//         Duration durationSinceCreation = Duration.between(unclosedShopCommand.getCreationTime(), Instant.now());
//         long minutesSinceCreation = durationSinceCreation.toMinutes();
//
//         if (minutesSinceCreation >= UNCLOSED_COMMAND_MAXIMUM_DURATION) {
//             sendPlayerMessage(player, TextFormatStyle.ERROR, format(
//                     "Unclosed command found, but was created %s minutes ago, which is longer than max allowed time (%s). Disregarding...",
//                     minutesSinceCreation,
//                     UNCLOSED_COMMAND_MAXIMUM_DURATION));
//
//             unclosedCommandRepository.delete(player.getUniqueID());
//             return;
//         }
//
//
//         //noinspection SwitchStatementWithTooFewBranches
//         switch (unclosedShopCommand.getUnclosedShopCommandType()) {
//             case CREATE:
//                 if (signInteractionType != PlayerSignInteractionType.RIGHT_CLICK) {
//                     return;
//                 }
//                 handleSignInteractionWithUnclosedCreateCommand(player, (UnclosedCreateShopCommand) unclosedShopCommand, tileEntitySign);
//                 break;
//             default:
//                 sendPlayerMessage(player, TextFormatStyle.ERROR, format(
//                         "Unhandled UnclosedShopCommandType '%s'! Please report this to the mod author.",
//                         unclosedShopCommand.getUnclosedShopCommandType()));
//         }
//     }
//
//     private void handleSignInteractionWithUnclosedCreateCommand(
//             EntityPlayer player,
//             UnclosedCreateShopCommand unclosedCreateShopCommand,
//             TileEntitySign tileEntitySign) {
//         BlockPos signPos = tileEntitySign.getPos();
//         World world = player.getEntityWorld();
//         BlockLocation signLocation = BlockLocation.of(world, signPos);
//         BlockPos chestPos = signPos.add(0, -1, 0);
//         BlockLocation chestLocation = BlockLocation.of(world, chestPos);
//         boolean isShopAlreadyLocatedAtLocation = grandSignShopRepository.retrieve(signLocation).isPresent();
//         boolean isWallSignAtEventLocation = (world.getBlockState(signPos).getBlock() instanceof BlockWallSign);
//         boolean signAlreadyHasText = !Arrays.stream(tileEntitySign.signText).map(ITextComponent::getFormattedText).allMatch(String::isEmpty);
//         boolean isChestLocatedUnderSign = world.getBlockState(chestPos).getBlock() instanceof BlockChest;
//         boolean isAnotherContainerAdjacentToChest = Stream.of(chestPos.north(), chestPos.east(), chestPos.south(), chestPos.west())
//                 .map(blockPos -> world.getBlockState(blockPos).getBlock())
//                 .anyMatch(block -> block instanceof BlockContainer);
//         if (isShopAlreadyLocatedAtLocation) {
//             sendPlayerMessage(player, TextFormatStyle.WARNING, "Cannot create shop: Shop already exists there.");
//             return;
//         }
//         if (signAlreadyHasText) {
//             sendPlayerMessage(player, TextFormatStyle.WARNING, "Cannot create shop: The sign must not have any text.");
//             return;
//         }
//         if (!isChestLocatedUnderSign) {
//             sendPlayerMessage(player, TextFormatStyle.WARNING, "Cannot create shop: No chest detected under sign.");
//             return;
//         }
//         if (isAnotherContainerAdjacentToChest) {
//             sendPlayerMessage(player, TextFormatStyle.WARNING, "Cannot create shop: Another container block is located next to the chest.");
//             return;
//         }
//         if (!isWallSignAtEventLocation) {
//             sendPlayerMessage(player, TextFormatStyle.TEST, "Not block wall sign.");
//             return;
//         }
//
//         String itemName = unclosedCreateShopCommand.getItemName();
//         Optional<Integer> metaOptional = unclosedCreateShopCommand.getMetadata();
//         PriceRange buyPrice = unclosedCreateShopCommand.getBuyPrice();
//         PriceRange sellPrice = unclosedCreateShopCommand.getSellPrice();
//
//         GrandSignShop grandSignShop = metaOptional
//                 .map(metadata -> GrandSignShop.of(player.getUniqueID(), signLocation, chestLocation, itemName, metadata, buyPrice, sellPrice))
//                 .orElseGet(() -> GrandSignShop.of(player.getUniqueID(), signLocation, chestLocation, itemName, buyPrice, sellPrice));
//         grandSignShopRepository.create(grandSignShop);
//
//         tileEntitySign.signText[0] = text("/^\\");
//         tileEntitySign.signText[1] = text("/___\\");
//         tileEntitySign.signText[2] = text("/(-_-)\\");
//         tileEntitySign.signText[3] = text("/_______\\");
//
//         tileEntitySign.markDirty();
//
//         sendPlayerMessage(player, TextFormatStyle.SUCCESS, format(
//                 "Successfully (kinda) handled create command! Read params: %s%s %s:%s %s:%s",
//                 itemName,
//                 metaOptional.map(aDouble -> format("@%s", aDouble)).orElse(""),
//                 buyPrice.getHigh(),
//                 buyPrice.getLow(),
//                 sellPrice.getHigh(),
//                 sellPrice.getLow()));
//         unclosedCommandRepository.delete(player.getUniqueID());
//     }
// }
