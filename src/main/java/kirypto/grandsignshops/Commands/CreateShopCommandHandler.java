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
        return "<item> <meta> <buy_price_high> <buy_price_low> <sell_price_high> <sell_price_low>";
    }

    @Override
    public String getSubCommandHelp() {
        return ("<item> and <meta> specifies the item type for the shop. <buy_price_low> and <sell_price_low> is used when the chest is nearly " +
                "empty, <buy_price_high> and <sell_price_high> is used when the chest is full, and buy/sell prices are interpolated between these " +
                "values depending how the full the chest is.");
    }

    @Override
    public void executeSubCommand(EntityPlayerMP player, List<String> commandArgs) {
        throw new NotImplementedException("Command not yet implemented");
    }
}
