package kirypto.grandsignshops;

import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Utilities {

    public static void sendPlayerMessage(EntityPlayer player, TextFormatStyle formatStyle, String message) {
        ITextComponent textComponent = text(message);
        textComponent.setStyle(formatStyle.getStyle());
        player.sendMessage(textComponent);

    }

    public static void sendPlayerMessage(EntityPlayer player, String message) {
        sendPlayerMessage(player, TextFormatStyle.NORMAL, message);
    }

    public static int parseInt(String input) throws NumberInvalidException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException var2) {
            throw new NumberInvalidException("commands.generic.num.invalid", input);
        }
    }

    public static ITextComponent text(String text) {
        return new TextComponentString(text);
    }

    @SuppressWarnings("WeakerAccess")
    public static class ForgeRegistryHelper {
        public static boolean isValidItem(String itemName) {
            return isValidItem(new ResourceLocation(itemName));
        }

        public static boolean isValidItem(ResourceLocation resourceLocation) {
            return ForgeRegistries.ITEMS.containsKey(resourceLocation);
        }

        public static Item getItem(String itemName) {
            return getItem(new ResourceLocation(itemName));
        }

        public static Item getItem(ResourceLocation resourceLocation) {
            return ForgeRegistries.ITEMS.getValue(resourceLocation);
        }
    }
}
