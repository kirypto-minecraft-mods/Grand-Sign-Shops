package kirypto.grandsignshops.Events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import kirypto.grandsignshops.PlayerSignInteractionType;
import kirypto.grandsignshops.Repository.GrandSignShopRepository;
import kirypto.grandsignshops.Repository.UnclosedCommandRepository;
import kirypto.grandsignshops.TextFormatStyle;
import kirypto.grandsignshops.UnclosedCommandParam;
import kirypto.grandsignshops.UnclosedShopCommand;

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
        Optional<UnclosedShopCommand> unclosedShopCommandOptional = unclosedCommandRepository.retrieveByPlayer(player.getUniqueID());
        if (unclosedShopCommandOptional.isPresent()) {
            UnclosedShopCommand unclosedShopCommand = unclosedShopCommandOptional.get();
            handleSignInteractionWithUnclosedShopCommand(player, unclosedShopCommand, tileEntitySign, signInteractionType);
        }
    }

    private void handleSignInteractionWithUnclosedShopCommand(
            EntityPlayer player,
            UnclosedShopCommand unclosedShopCommand,
            TileEntitySign tileEntitySign, PlayerSignInteractionType signInteractionType) {
        Duration durationSinceCreation = Duration.between(unclosedShopCommand.getCreationTime(), Instant.now());
        long minutesSinceCreation = durationSinceCreation.toMinutes();

        if (minutesSinceCreation >= UNCLOSED_COMMAND_MAXIMUM_DURATION) {
            sendPlayerMessage(player, TextFormatStyle.ERROR, format(
                    "Unclosed command found, but was created %s minutes ago, which is longer than max allowed time (%s). Disregarding...",
                    minutesSinceCreation,
                    UNCLOSED_COMMAND_MAXIMUM_DURATION));

            unclosedCommandRepository.clearByPlayer(player.getUniqueID());
            return;
        }


        //noinspection SwitchStatementWithTooFewBranches
        switch (unclosedShopCommand.getUnclosedShopCommandType()) {
            case CREATE:
                handleSignInteractionWithUnclosedCreateCommand(player, unclosedShopCommand, tileEntitySign, signInteractionType);
                break;
            default:
                sendPlayerMessage(player, TextFormatStyle.ERROR, format(
                        "Unhandled UnclosedShopCommandType '%s'! Please report this to the mod author.",
                        unclosedShopCommand.getUnclosedShopCommandType()));
        }
    }

    private void handleSignInteractionWithUnclosedCreateCommand(
            EntityPlayer player,
            UnclosedShopCommand unclosedShopCommand,
            TileEntitySign tileEntitySign,
            PlayerSignInteractionType signInteractionType) {

        Map<UnclosedCommandParam, Object> commandParams = unclosedShopCommand.getParams();

        ResourceLocation item = (ResourceLocation) commandParams.get(UnclosedCommandParam.ITEM);
        Optional<Integer> metaOptional = Optional.ofNullable((Integer) commandParams.get(UnclosedCommandParam.META));
        int buyPriceHigh = (int) commandParams.get(UnclosedCommandParam.BUY_HIGH);
        int buyPriceLow = (int) commandParams.get(UnclosedCommandParam.BUY_LOW);
        int sellPriceHigh = (int) commandParams.get(UnclosedCommandParam.SELL_HIGH);
        int sellPriceLow = (int) commandParams.get(UnclosedCommandParam.SELL_LOW);

        tileEntitySign.signText[0] = text("/^\\");
        tileEntitySign.signText[1] = text("/___\\");
        tileEntitySign.signText[2] = text("/(-_-)\\");
        tileEntitySign.signText[3] = text("/_______\\");

        sendPlayerMessage(player, TextFormatStyle.SUCCESS, format(
                "Successfully (kinda) handled create command! Read params: %s%s %s:%s %s:%s",
                item,
                metaOptional.map(aDouble -> format("@%s", aDouble)).orElse(""),
                buyPriceHigh,
                buyPriceLow,
                sellPriceHigh,
                sellPriceLow));
        unclosedCommandRepository.clearByPlayer(player.getUniqueID());
    }
}
