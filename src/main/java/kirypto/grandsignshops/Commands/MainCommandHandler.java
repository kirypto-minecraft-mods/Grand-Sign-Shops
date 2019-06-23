package kirypto.grandsignshops.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.ParametersAreNonnullByDefault;

import kirypto.grandsignshops.Repository.UnclosedCommandRepository;
import mcp.MethodsReturnNonnullByDefault;

import static java.lang.String.format;
import static kirypto.grandsignshops.Utilities.sendPlayerMessage;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MainCommandHandler extends CommandBase {
    private static final String GRAND_SIGH_SHOPS_ROOT_COMMAND = "gshops";

    private final Map<String, GShopsSubCommandHandler> subCommandHandlers;

    public MainCommandHandler(UnclosedCommandRepository unclosedCommandRepository) {
        this.subCommandHandlers = Stream.of(
                new CreateShopCommandHandler(unclosedCommandRepository),
                new TestHandler()
        ).collect(Collectors.toMap(GShopsSubCommandHandler::getSubCommandName, subCommandHandler -> subCommandHandler));
    }

    @Override
    public String getName() {
        return GRAND_SIGH_SHOPS_ROOT_COMMAND;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return format("/%s <sub-command> [parameters]\n    Available sub-commands: %s",
                      GRAND_SIGH_SHOPS_ROOT_COMMAND,
                      String.join(", ", subCommandHandlers.keySet())
        );
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] argsRaw) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            throw new CommandException("Grand Sign Shops can only be used by players.");
        }
        EntityPlayerMP player = (EntityPlayerMP) sender;

        List<String> commandArgs = new ArrayList<>(Arrays.asList(argsRaw));

        if (commandArgs.size() < 1) {
            throw new WrongUsageException(getUsage(sender));
        }

        String subCommand = commandArgs.remove(0);

        if (subCommand.equals("help")) {
            getHelp(player, commandArgs);
            return;
        }

        if (!subCommandHandlers.containsKey(subCommand)) {
            throw new WrongUsageException(format("%s has no sub-command '%s'", GRAND_SIGH_SHOPS_ROOT_COMMAND, subCommand));
        }

        subCommandHandlers.get(subCommand).executeSubCommand(player, commandArgs);
    }

    private void getHelp(EntityPlayerMP player, List<String> commandArgs) throws WrongUsageException {
        if (commandArgs.size() != 1) {
            throw new WrongUsageException(format("No sub-command provided. Usage: /%s help <sub-command>", GRAND_SIGH_SHOPS_ROOT_COMMAND));
        }
        String subCommand = commandArgs.get(0);

        if (!subCommandHandlers.containsKey(subCommand)) {
            throw new WrongUsageException(format("%s has no sub-command '%s'", GRAND_SIGH_SHOPS_ROOT_COMMAND, subCommand));
        }

        GShopsSubCommandHandler subCommandHandler = subCommandHandlers.get(subCommand);
        sendPlayerMessage(player, format("Usage: /%s %s %s\n    Help: %s",
                                         GRAND_SIGH_SHOPS_ROOT_COMMAND,
                                         subCommand,
                                         subCommandHandler.getSubCommandUsage(),
                                         subCommandHandler.getSubCommandHelp()));
    }
}

