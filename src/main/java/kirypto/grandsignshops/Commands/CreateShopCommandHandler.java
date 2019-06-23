package kirypto.grandsignshops.Commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;

import static java.lang.String.format;
import static kirypto.grandsignshops.Utilities.parseInt;
import static kirypto.grandsignshops.Utilities.sendPlayerMessage;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CreateShopCommandHandler implements GShopsSubCommandHandler {

    public CreateShopCommandHandler() {
    }

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
    public void executeSubCommand(EntityPlayerMP player, List<String> commandArgs) throws CommandException {
        if (commandArgs.size() != 6) {
            throw new WrongUsageException(format("%s command requires: %s", getSubCommandName(), getSubCommandUsage()));
        }

        ResourceLocation item = new ResourceLocation(commandArgs.get(0));
        int meta = parseInt(commandArgs.get(1));
        int buyPriceHigh = parseInt(commandArgs.get(2));
        int buyPriceLow = parseInt(commandArgs.get(3));
        int sellPriceHigh = parseInt(commandArgs.get(4));
        int sellPriceLow = parseInt(commandArgs.get(5));

        boolean isValidRequest = ForgeRegistries.BLOCKS.containsKey(item) || ForgeRegistries.ITEMS.containsKey(item);
        if (!isValidRequest) {
            throw new WrongUsageException("Error: Item not found");
        }
        if (meta < 0) {
            throw new WrongUsageException("Error: Invalid meta");
        }
        if (buyPriceLow <= 0) {
            throw new WrongUsageException("Error: Buy price (low) cannot be less than 1");
        }
        if (buyPriceHigh < buyPriceLow) {
            throw new WrongUsageException("Error: Buy price (high) cannot be less than buy price (low)");
        }
        if (sellPriceLow <= 0) {
            throw new WrongUsageException("Error: Sell price (low) cannot be less than 1");
        }
        if (sellPriceHigh < buyPriceLow) {
            throw new WrongUsageException("Error: Sell price (high) cannot be less than buy price (low)");
        }

        sendPlayerMessage(player, format("Success: %s %s:%s %s:%s", item, buyPriceHigh, buyPriceLow, sellPriceHigh, sellPriceLow));
    }
}
