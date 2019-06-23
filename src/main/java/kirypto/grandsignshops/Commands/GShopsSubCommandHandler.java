package kirypto.grandsignshops.Commands;

import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface GShopsSubCommandHandler {

    String getSubCommandName();

    String getSubCommandUsage();

    String getSubCommandHelp();

    void executeSubCommand(EntityPlayerMP player, List<String> commandArgs);
}
