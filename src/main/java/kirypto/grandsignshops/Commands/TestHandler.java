package kirypto.grandsignshops.Commands;

import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import kirypto.grandsignshops.Utilities;
import mcp.MethodsReturnNonnullByDefault;

import static java.lang.String.format;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
class TestHandler implements GShopsSubCommandHandler {

    @Override
    public String getSubCommandName() {
        return "test";
    }

    @Override
    public String getSubCommandHelp() {
        return "item amount";
    }

    @Override
    public void executeSubCommand(EntityPlayerMP player, List<String> commandArgs) {
        Utilities.sendPlayerMessage(player, format("Successfully ran command %s!", getSubCommandName()));
    }
}
