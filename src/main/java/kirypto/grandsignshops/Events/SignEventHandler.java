package kirypto.grandsignshops.Events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySign;

import java.time.Duration;
import java.time.Instant;

import kirypto.grandsignshops.PlayerSignInteractionType;
import kirypto.grandsignshops.Repository.UnclosedCommandRepository;
import kirypto.grandsignshops.TextFormatStyle;

import static java.lang.String.format;
import static kirypto.grandsignshops.Utilities.sendPlayerMessage;

public class SignEventHandler {
    private static final int UNCLOSED_COMMAND_MAXIMUM_DURATION = 2;

    private final UnclosedCommandRepository unclosedCommandRepository;

    public SignEventHandler(UnclosedCommandRepository unclosedCommandRepository) {
        this.unclosedCommandRepository = unclosedCommandRepository;
    }

    public void handleSignClick(EntityPlayer player, TileEntitySign tileEntitySign, PlayerSignInteractionType signInteractionType) {

        unclosedCommandRepository.retrieveByPlayer(player.getUniqueID()).ifPresent(unclosedShopCommand -> {
            Duration durationSinceCreation = Duration.between(unclosedShopCommand.getCreationTime(), Instant.now());
            long minutesSinceCreation = durationSinceCreation.toMinutes();

            if (minutesSinceCreation >= UNCLOSED_COMMAND_MAXIMUM_DURATION) {
                sendPlayerMessage(player, TextFormatStyle.RED, format(
                        "Unclosed command found, but was created %s minutes ago, which is longer than max allowed time (%s). Disregarding...",
                        minutesSinceCreation,
                        UNCLOSED_COMMAND_MAXIMUM_DURATION));

                unclosedCommandRepository.clearByPlayer(player.getUniqueID());
                return;
            }

            sendPlayerMessage(player, format("Found matching command, created %s seconds ago.", durationSinceCreation.getSeconds()));
        });
    }
}
