package kirypto.grandsignshops.Events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.TextComponentString;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

import kirypto.grandsignshops.PlayerSignInteractionType;
import kirypto.grandsignshops.Repository.UnclosedCommandRepository;
import the_fireplace.grandeconomy.api.GrandEconomyApi;

import static java.lang.String.format;
import static kirypto.grandsignshops.Utilities.sendPlayerMessage;

public class SignEventHandler {

    private final UnclosedCommandRepository unclosedCommandRepository;

    public SignEventHandler(UnclosedCommandRepository unclosedCommandRepository) {
        this.unclosedCommandRepository = unclosedCommandRepository;
    }

    public void handleSignClick(EntityPlayer player, TileEntitySign tileEntitySign, PlayerSignInteractionType signInteractionType) {
        String fullSignText = Arrays.stream(tileEntitySign.signText)
                .map(iTextComp -> format("[%s, %s]", iTextComp.getFormattedText(), iTextComp.getUnformattedComponentText()))
                .collect(Collectors.joining());

        if (!fullSignText.contains("password")) {
            sendPlayerMessage(player, format("The sign did not have the pass phrase... It just said '%s'", fullSignText));
            return;
        }

        int earnings = 42;
        sendPlayerMessage(player, format("You just %s clicked on a sign with the pass phrase, earning %s!", signInteractionType, earnings));
        GrandEconomyApi.addToBalance(player.getUniqueID(), earnings, true);

        tileEntitySign.signText[0] = new TextComponentString("Test");

        unclosedCommandRepository.retrieveByPlayer(player.getUniqueID()).ifPresent(unclosedShopCommand -> {
            Duration timeSinceCration = Duration.between(unclosedShopCommand.getCreationTime(), Instant.now());
            sendPlayerMessage(player, format("Found matching command, created %s ago.", timeSinceCration));
        });
    }
}
