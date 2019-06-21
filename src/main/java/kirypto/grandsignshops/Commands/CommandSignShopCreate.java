package kirypto.grandsignshops.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;

import kirypto.grandsignshops.IGrandSignShopRepository;
import mcp.MethodsReturnNonnullByDefault;

import static java.lang.String.format;
import static kirypto.grandsignshops.Utilities.sendPlayerMessage;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CommandSignShopCreate extends CommandBase {
    private final IGrandSignShopRepository grandSignShopRepository;

    public CommandSignShopCreate(IGrandSignShopRepository grandSignShopRepository) {
        this.grandSignShopRepository = grandSignShopRepository;
    }

    @Override
    public String getName() {
        return "gshops_old";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/gshops_old create <item> <meta> <buy_price_high> <buy_price_low> <sell_price_high> <sell_price_low>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 6) {
            throw new WrongUsageException(getUsage(sender));
        }
        if (!(sender instanceof EntityPlayerMP)) {
            throw new CommandException("You must be a player to do this");
        }

        ResourceLocation item = new ResourceLocation(args[0]);
        int meta = parseInt(args[1]);
        int buyPriceHigh = parseInt(args[2]);
        int buyPriceLow = parseInt(args[3]);
        int sellPriceHigh = parseInt(args[4]);
        int sellPriceLow = parseInt(args[5]);

        boolean isValidRequest = ForgeRegistries.BLOCKS.containsKey(item) || ForgeRegistries.ITEMS.containsKey(item);
        if (!isValidRequest) {
            throw new CommandException("Error: Item not found");
        }
        if (meta < 0) {
            throw new CommandException("Error: Invalid meta");
        }
        if (buyPriceLow <= 0) {
            throw new CommandException("Error: Buy price (low) cannot be less than 1");
        }
        if (buyPriceHigh < buyPriceLow) {
            throw new CommandException("Error: Buy price (high) cannot be less than buy price (low)");
        }
        if (sellPriceLow <= 0) {
            throw new CommandException("Error: Sell price (low) cannot be less than 1");
        }
        if (sellPriceHigh < buyPriceLow) {
            throw new CommandException("Error: Sell price (high) cannot be less than buy price (low)");
        }

        EntityPlayerMP playerMP = (EntityPlayerMP) sender;
        sendPlayerMessage(playerMP, format("Success: %s %s:%s %s:%s", item, buyPriceHigh, buyPriceLow, sellPriceHigh, sellPriceLow));
    }
}
