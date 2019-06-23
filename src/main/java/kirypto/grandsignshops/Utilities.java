package kirypto.grandsignshops;

import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class Utilities {
    public static void sendPlayerMessage(EntityPlayer player, String message) {
        player.sendMessage(new TextComponentString(message));
    }

    public static int parseInt(String input) throws NumberInvalidException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException var2) {
            throw new NumberInvalidException("commands.generic.num.invalid", input);
        }
    }
}
