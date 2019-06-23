package kirypto.grandsignshops;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.DimensionType;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;
import java.util.Optional;

import kirypto.grandsignshops.Commands.CommandSignShopCreate;
import kirypto.grandsignshops.Commands.MainCommandHandler;
import kirypto.grandsignshops.Repository.GrandSignShopRepository;
import kirypto.grandsignshops.Repository.JsonGrandSignShopRepository;
import the_fireplace.grandeconomy.economy.Account;

@Mod(modid = GrandSignShops.MOD_ID, name = GrandSignShops.MODNAME, version = GrandSignShops.VERSION,
        acceptedMinecraftVersions = "1.12.2", acceptableRemoteVersions = "*")
public final class GrandSignShops {
    public static final String MOD_ID = "grandsignshops";
    public static final String MODNAME = "Grand Sign Shops";
    public static final String VERSION = "1.0.0-alpha2";

    @Mod.Instance(GrandSignShops.MOD_ID)
    public static GrandSignShops instance;

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        ICommandManager command = server.getCommandManager();
        ServerCommandManager manager = (ServerCommandManager) command;

        Account.clear();

        getWorldSaveFolder(server)
                .map(worldSaveFolder -> new File(worldSaveFolder, "GrandSignShops"))
                .ifPresent(grandSignShopsRootFolder -> {
                    GrandSignShopRepository grandSignShopRepository = new JsonGrandSignShopRepository(grandSignShopsRootFolder);

                    manager.registerCommand(new CommandSignShopCreate(grandSignShopRepository));
                    manager.registerCommand(new MainCommandHandler());
                });
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
    }

    private Optional<File> getWorldSaveFolder(MinecraftServer server) {
        ISaveHandler handler = server.getWorld(DimensionType.OVERWORLD.getId()).getSaveHandler();
        if (!(handler instanceof SaveHandler)) {
            return Optional.empty();
        }
        return Optional.of(handler.getWorldDirectory());
    }
}
