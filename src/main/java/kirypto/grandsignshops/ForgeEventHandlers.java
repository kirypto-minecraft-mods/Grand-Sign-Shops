package kirypto.grandsignshops;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.stream.Collectors;

import the_fireplace.grandeconomy.api.GrandEconomyApi;

import static java.lang.String.format;

public class ForgeEventHandlers {

    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (!(event instanceof PlayerInteractEvent.LeftClickBlock) && !(event instanceof PlayerInteractEvent.RightClickBlock)) {
            return;
        }

        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Block block = world.getBlockState(pos).getBlock();
        String clickType = (event instanceof PlayerInteractEvent.LeftClickBlock) ? "left" : "right";


        if (!(block instanceof BlockSign)) {
            sendPlayerMessage(player, "Not a sign block.");
            return;
        }

        TileEntity tileEntity = world.getTileEntity(pos);
        if (!(tileEntity instanceof TileEntitySign)) {
            sendPlayerMessage(player, "Not a sign entity.");
            return;
        }

        TileEntitySign tileEntitySign = (TileEntitySign) tileEntity;

        String fullSignText = Arrays.stream(tileEntitySign.signText)
                .map(iTextComp -> format("[%s, %s]", iTextComp.getFormattedText(), iTextComp.getUnformattedComponentText()))
                .collect(Collectors.joining());

        if (fullSignText.contains("password")) {
            int earnings = 42;
            sendPlayerMessage(player, format("You just %s clicked on a sign with the pass phrase, earning %s!", clickType, earnings));
            GrandEconomyApi.addToBalance(player.getUniqueID(), earnings, true);

            tileEntitySign.signText[0] = new TextComponentString("Test");

        } else {
            sendPlayerMessage(player, format("The sign did not have the pass phrase... It just said '%s'", fullSignText));
        }
    }

    private void sendPlayerMessage(EntityPlayer player, String message) {
        player.sendMessage(new TextComponentString(message));
    }
}
