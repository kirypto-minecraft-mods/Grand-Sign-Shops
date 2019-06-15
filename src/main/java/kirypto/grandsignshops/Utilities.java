package kirypto.grandsignshops;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class Utilities {
    public static void sendPlayerMessage(EntityPlayer player, String message) {
        player.sendMessage(new TextComponentString(message));
    }
}
