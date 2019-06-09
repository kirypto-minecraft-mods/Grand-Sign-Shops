package kirypto.grandsignshops;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = GrandSignShops.MOD_ID, name = GrandSignShops.MODNAME, version = GrandSignShops.VERSION,
        acceptedMinecraftVersions = "1.12.2", acceptableRemoteVersions = "*")
public final class GrandSignShops {
    public static final String MOD_ID = "grandsignshops";
    public static final String MODNAME = "Grand Sign Shops";
    public static final String VERSION = "${version}";

    @Mod.Instance(GrandSignShops.MOD_ID)
    public static GrandSignShops instance;

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        ICommandManager command = server.getCommandManager();
        ServerCommandManager manager = (ServerCommandManager) command;
    }
}
