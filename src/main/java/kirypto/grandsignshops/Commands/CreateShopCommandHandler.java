package kirypto.grandsignshops.Commands;

import net.minecraft.entity.player.EntityPlayerMP;

import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CreateShopCommandHandler implements GShopsSubCommandHandler {
    @Override
    public String getSubCommandName() {
        return "create";
    }

    @Override
    public String getSubCommandUsage() {
        return "<item> <meta> <buy_high> <buy_low> <sell_high> <sell_low>";
    }

    @Override
    public String getSubCommandHelp() {
        return ("\n    - <item> and <meta> specifies the item type for the shop." +
                "\n    - <buy_low> and <sell_low> is used when the chest is nearly empty" +
                "\n    - <buy_high> and <sell_high> is used when the chest is full" +
                "\n    - buy/sell prices are interpolated between these values depending how the full the chest is.");
    }

    @Override
    public void executeSubCommand(EntityPlayerMP player, List<String> commandArgs) {
        throw new NotImplementedException("Command not yet implemented");
    }
}
