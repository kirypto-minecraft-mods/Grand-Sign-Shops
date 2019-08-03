package kirypto.grandsignshops.Commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.ParametersAreNonnullByDefault;

import kirypto.grandsignshops.Repository.UnclosedCommandRepository;
import kirypto.grandsignshops.UnclosedCommandParam;
import kirypto.grandsignshops.UnclosedShopCommand;
import kirypto.grandsignshops.UnclosedShopCommandType;
import kirypto.grandsignshops.Utilities.ForgeRegistryHelper;
import mcp.MethodsReturnNonnullByDefault;

import static java.lang.String.format;
import static kirypto.grandsignshops.Utilities.parseInt;
import static kirypto.grandsignshops.Utilities.sendPlayerMessage;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CreateShopCommandHandler implements GShopsSubCommandHandler {

    private final UnclosedCommandRepository unclosedCommandRepository;

    public CreateShopCommandHandler(UnclosedCommandRepository unclosedCommandRepository) {
        this.unclosedCommandRepository = unclosedCommandRepository;
    }

    @Override
    public String getSubCommandName() {
        return "create";
    }

    @Override
    public String getSubCommandUsage() {
        return "<item>[@meta] <sell_high> <sell_low> <buy_high> <buy_low>";
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
        if (commandArgs.size() != 5) {
            throw new WrongUsageException(format("%s command requires: %s", getSubCommandName(), getSubCommandUsage()));
        }

        String[] itemAndOptionalMeta = commandArgs.get(0).split("@");

        String itemName = itemAndOptionalMeta[0];
        Optional<Integer> metaOptional = (itemAndOptionalMeta.length > 1 ? Optional.of(parseInt(itemAndOptionalMeta[1])) : Optional.empty());
        int sellPriceHigh = parseInt(commandArgs.get(1));
        int sellPriceLow = parseInt(commandArgs.get(2));
        int buyPriceHigh = parseInt(commandArgs.get(3));
        int buyPriceLow = parseInt(commandArgs.get(4));

        if (!ForgeRegistryHelper.isValidItem(itemName)) {
            throw new WrongUsageException("Error: Item not found");
        }
        if (metaOptional.isPresent() && metaOptional.get() < 0) {
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
        if (sellPriceHigh < sellPriceLow) {
            throw new WrongUsageException("Error: Sell price (high) cannot be less than buy price (low)");
        }

        Map<UnclosedCommandParam, Object> commandParameters = new LinkedHashMap<>();
        commandParameters.put(UnclosedCommandParam.ITEM, itemName);
        commandParameters.put(UnclosedCommandParam.META, metaOptional.orElse(null));
        commandParameters.put(UnclosedCommandParam.BUY_HIGH, buyPriceHigh);
        commandParameters.put(UnclosedCommandParam.BUY_LOW, buyPriceLow);
        commandParameters.put(UnclosedCommandParam.SELL_HIGH, sellPriceHigh);
        commandParameters.put(UnclosedCommandParam.SELL_LOW, sellPriceLow);
        unclosedCommandRepository.save(UnclosedShopCommand.of(UnclosedShopCommandType.CREATE, player.getUniqueID(), commandParameters));
        sendPlayerMessage(player, format("Success: %s %s:%s %s:%s", itemName, buyPriceHigh, buyPriceLow, sellPriceHigh, sellPriceLow));
    }
}
