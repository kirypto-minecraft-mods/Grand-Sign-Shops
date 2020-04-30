// package kirypto.grandsignshops.CommandHandlers;
//
// import net.minecraft.command.CommandException;
// import net.minecraft.command.WrongUsageException;
// import net.minecraft.entity.player.EntityPlayerMP;
//
// import java.util.List;
//
// import javax.annotation.ParametersAreNonnullByDefault;
//
// import kirypto.grandsignshops.domain.repository.UnclosedCommandRepository;
// import mcp.MethodsReturnNonnullByDefault;
//
// import static java.lang.String.format;
// import static kirypto.grandsignshops.Utilities.sendPlayerMessage;
//
// @ParametersAreNonnullByDefault
// @MethodsReturnNonnullByDefault
// public class ClearExistingCommandsCommandHandler implements GShopsSubCommandHandler {
//
//     private final UnclosedCommandRepository unclosedCommandRepository;
//
//     public ClearExistingCommandsCommandHandler(UnclosedCommandRepository unclosedCommandRepository) {
//         this.unclosedCommandRepository = unclosedCommandRepository;
//     }
//
//     @Override
//     public String getSubCommandName() {
//         return "clear";
//     }
//
//     @Override
//     public String getSubCommandUsage() {
//         return "";
//     }
//
//     @Override
//     public String getSubCommandHelp() {
//         return "\n    - clears any awaiting unfinished commands.";
//     }
//
//     @Override
//     public void executeSubCommand(EntityPlayerMP player, List<String> commandArgs) throws CommandException {
//         if (commandArgs.size() != 0) {
//             throw new WrongUsageException(format("%s command takes no arguments", getSubCommandName()));
//         }
//
//         unclosedCommandRepository.delete(player.getUniqueID());
//
//         sendPlayerMessage(player, "Successfully cleared any existing shop commands.");
//     }
// }
