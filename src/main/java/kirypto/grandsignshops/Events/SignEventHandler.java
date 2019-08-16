package kirypto.grandsignshops.Events;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockWallSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import kirypto.grandsignshops.BlockLocation;
import kirypto.grandsignshops.GrandSignShop;
import kirypto.grandsignshops.PlayerSignInteractionType;
import kirypto.grandsignshops.PriceRange;
import kirypto.grandsignshops.Repository.GrandSignShopRepository;
import kirypto.grandsignshops.Repository.UnclosedCommandRepository;
import kirypto.grandsignshops.TextFormatStyle;
import kirypto.grandsignshops.UnclosedShopCommands.UnclosedCreateShopCommand;
import kirypto.grandsignshops.UnclosedShopCommands.UnclosedShopCommand;

import static java.lang.String.format;
import static kirypto.grandsignshops.Utilities.sendPlayerMessage;
import static kirypto.grandsignshops.Utilities.text;

public class SignEventHandler {
    private static final int UNCLOSED_COMMAND_MAXIMUM_DURATION = 2;

    private final UnclosedCommandRepository unclosedCommandRepository;
    private final GrandSignShopRepository grandSignShopRepository;

    public SignEventHandler(
            UnclosedCommandRepository unclosedCommandRepository,
            GrandSignShopRepository grandSignShopRepository) {
        this.unclosedCommandRepository = unclosedCommandRepository;
        this.grandSignShopRepository = grandSignShopRepository;
    }

    public void handleSignClick(EntityPlayer player, TileEntitySign tileEntitySign, PlayerSignInteractionType signInteractionType) {
        Optional<UnclosedShopCommand> unclosedShopCommandOptional = unclosedCommandRepository.retrieve(player.getUniqueID());
        if (unclosedShopCommandOptional.isPresent()) {
            UnclosedShopCommand unclosedShopCommand = unclosedShopCommandOptional.get();
            handleSignInteractionWithUnclosedShopCommand(player, unclosedShopCommand, tileEntitySign, signInteractionType);
        }
    }

    private void handleSignInteractionWithUnclosedShopCommand(
            EntityPlayer player,
            UnclosedShopCommand unclosedShopCommand,
            TileEntitySign tileEntitySign,
            PlayerSignInteractionType signInteractionType) {
        Duration durationSinceCreation = Duration.between(unclosedShopCommand.getCreationTime(), Instant.now());
        long minutesSinceCreation = durationSinceCreation.toMinutes();

        if (minutesSinceCreation >= UNCLOSED_COMMAND_MAXIMUM_DURATION) {
            sendPlayerMessage(player, TextFormatStyle.ERROR, format(
                    "Unclosed command found, but was created %s minutes ago, which is longer than max allowed time (%s). Disregarding...",
                    minutesSinceCreation,
                    UNCLOSED_COMMAND_MAXIMUM_DURATION));

            unclosedCommandRepository.delete(player.getUniqueID());
            return;
        }


        //noinspection SwitchStatementWithTooFewBranches
        switch (unclosedShopCommand.getUnclosedShopCommandType()) {
            case CREATE:
                if (signInteractionType != PlayerSignInteractionType.RIGHT_CLICK) {
                    return;
                }
                handleSignInteractionWithUnclosedCreateCommand(player, (UnclosedCreateShopCommand) unclosedShopCommand, tileEntitySign);
                break;
            default:
                sendPlayerMessage(player, TextFormatStyle.ERROR, format(
                        "Unhandled UnclosedShopCommandType '%s'! Please report this to the mod author.",
                        unclosedShopCommand.getUnclosedShopCommandType()));
        }
    }

    private void handleSignInteractionWithUnclosedCreateCommand(
            EntityPlayer player,
            UnclosedCreateShopCommand unclosedCreateShopCommand,
            TileEntitySign tileEntitySign) {
        BlockPos signPos = tileEntitySign.getPos();
        BlockLocation signLocation = BlockLocation.of(player.getEntityWorld(), signPos);
        BlockPos chestPos = signPos.add(0, -1, 0);
        BlockLocation chestLocation = BlockLocation.of(player.getEntityWorld(), chestPos);
        if (!(player.getEntityWorld().getBlockState(signPos).getBlock() instanceof BlockWallSign)) {
            sendPlayerMessage(player, TextFormatStyle.TEST, "Not block wall sign.");
            return;
        } else if (!(player.getEntityWorld().getBlockState(chestPos).getBlock() instanceof BlockChest)) {
            sendPlayerMessage(player, TextFormatStyle.WARNING, "Cannot create shop: No chest detected under sign.");
            return;
        } else if (!Arrays.stream(tileEntitySign.signText).map(ITextComponent::getFormattedText).allMatch(String::isEmpty)) {
            sendPlayerMessage(player, TextFormatStyle.WARNING, "Cannot create shop: The sign must not have any text.");
            return;
        }

        if (grandSignShopRepository.retrieve(signLocation).isPresent()) {
            sendPlayerMessage(player, TextFormatStyle.ERROR, "Cannot create shop: Shop already exists there.");
            return;
        }

        String itemName = unclosedCreateShopCommand.getItemName();
        Optional<Integer> metaOptional = unclosedCreateShopCommand.getMetadata();
        PriceRange buyPrice = unclosedCreateShopCommand.getBuyPrice();
        PriceRange sellPrice = unclosedCreateShopCommand.getSellPrice();

        GrandSignShop grandSignShop = metaOptional
                .map(metadata -> GrandSignShop.of(player.getUniqueID(), signLocation, chestLocation, itemName, metadata, buyPrice, sellPrice))
                .orElseGet(() -> GrandSignShop.of(player.getUniqueID(), signLocation, chestLocation, itemName, buyPrice, sellPrice));
        grandSignShopRepository.create(grandSignShop);

        tileEntitySign.signText[0] = text("/^\\");
        tileEntitySign.signText[1] = text("/___\\");
        tileEntitySign.signText[2] = text("/(-_-)\\");
        tileEntitySign.signText[3] = text("/_______\\");

        tileEntitySign.markDirty();

        sendPlayerMessage(player, TextFormatStyle.SUCCESS, format(
                "Successfully (kinda) handled create command! Read params: %s%s %s:%s %s:%s",
                itemName,
                metaOptional.map(aDouble -> format("@%s", aDouble)).orElse(""),
                buyPrice.getHigh(),
                buyPrice.getLow(),
                sellPrice.getHigh(),
                sellPrice.getLow()));
        unclosedCommandRepository.delete(player.getUniqueID());
    }
}
