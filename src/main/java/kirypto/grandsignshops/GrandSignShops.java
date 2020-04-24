package kirypto.grandsignshops;

import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(GrandSignShops.MOD_ID)
public final class GrandSignShops {
    public static final String MOD_ID = "grandsignshops";
    public static final String MODNAME = "Grand Sign Shops";
    public static final String VERSION = "1.0.0-alpha2";
    private final Logger log;

    public GrandSignShops() {
        log = LogManager.getLogger();
    }

    // @Mod.Instance(GrandSignShops.MOD_ID)
    // public static GrandSignShops instance;

    // @SideOnly(value = Side.SERVER)
    // @EventHandler
    public void onServerStart(FMLDedicatedServerSetupEvent event) {
        // System.out.println("@@@@@@@@@@@@@@@@@@@@ On Server Start");
        DedicatedServer dedicatedServer = event.getServerSupplier().get();
        // MinecraftServer server = event.getServer();
        Commands commandManager = dedicatedServer.getCommandManager();
        // ICommandManager command = server.getCommandManager();

        // ServerCommandManager manager = (ServerCommandManager) command;

        File worldSaveFolder = getWorldSaveFolder(dedicatedServer);
        File grandSignShopsRootFolder = new File(worldSaveFolder, "GrandSignShops");
        // getWorldSaveFolder(server)
        //         .map(worldSaveFolder -> new File(worldSaveFolder, "GrandSignShops"))
        //         .ifPresent(grandSignShopsRootFolder -> {
        // GrandSignShopRepository grandSignShopRepository = new JsonGrandSignShopRepository(grandSignShopsRootFolder);
        // UnclosedCommandRepository unclosedCommandRepository = new InMemoryUnclosedCommandRepository();

        // commandManager.getDispatcher().register(new LiteralArgumentBuilder<>()).registerCommand(new MainCommandHandler(unclosedCommandRepository));
        // MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers(unclosedCommandRepository, grandSignShopRepository));
        //         });
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        log.info("onServerStarting");
        event.getServer().getCommandManager().getDispatcher().register(Commands.literal("import").executes(command -> {
            log.info("/import command dispatched");
            //importScreen.show();
            return 0;
        }));
    }

    private File getWorldSaveFolder(MinecraftServer server) {
        SaveHandler saveHandler = server.getWorld(DimensionType.OVERWORLD).getSaveHandler();
        return saveHandler.getWorldDirectory();
    }
}
