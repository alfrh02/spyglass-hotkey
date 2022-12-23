package land.alfred.spyglasshotkey.server.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class SpyglassHotkeyPacket {

    public static boolean swapped = false;

    public static int spyglassSlot = 256;
    public static int currentSlot = 256;
    public static ItemStack handStackInfo;
    public static ItemStack spyglassStackInfo;

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // assign buf itemstack to respective itemstack variables
        if (buf.readItemStack().getItem() == Items.SPYGLASS) {
            spyglassStackInfo = buf.readItemStack();
        } else {
            handStackInfo = buf.readItemStack();
        }

        if (spyglassStackInfo != null && handStackInfo != null) {
            if (player.getInventory().getStack(player.getInventory().selectedSlot).getItem() == Items.SPYGLASS && spyglassSlot != 256) {
                player.setStackInHand(Hand.MAIN_HAND, handStackInfo);
                player.getInventory().setStack(spyglassSlot, spyglassStackInfo);
            } else {
                spyglassSlot = player.getInventory().getSlotWithStack(new ItemStack(Items.SPYGLASS));
                currentSlot = player.getInventory().selectedSlot;
            }
            player.setStackInHand(Hand.MAIN_HAND, spyglassStackInfo);
            player.getInventory().setStack(spyglassSlot, handStackInfo);
        }

    }
}
