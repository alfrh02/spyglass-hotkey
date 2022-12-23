package land.alfred.spyglasshotkey.client;

import land.alfred.spyglasshotkey.SpyglassHotkey;
import land.alfred.spyglasshotkey.server.SpyglassHotkeyServer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class SpyglassHotkeyClient implements ClientModInitializer {

    public static KeyBinding spyglassKeybind;
    public ItemStack spyglassStackInfo = null;
    public ItemStack handStackInfo = null;
    public int spyglassSlot = 256;
    public int currentSlot = 256;

    @Override
    public void onInitializeClient() {
        spyglassKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key." + SpyglassHotkey.MODID + ".spyglass",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "category." + SpyglassHotkey.MODID + ".keybind"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (spyglassKeybind.wasPressed()) {
                if (client.player.getInventory().contains(new ItemStack(Items.SPYGLASS))) {
                    if (client.player.getOffHandStack().getItem() == Items.SPYGLASS) {
                        continue;
                    }
                    if (client.player.getInventory().getStack(client.player.getInventory().selectedSlot).getItem() == Items.SPYGLASS && spyglassStackInfo != null) {
                        client.player.setStackInHand(Hand.MAIN_HAND, handStackInfo);
                        client.player.getInventory().setStack(spyglassSlot, spyglassStackInfo);
                        ClientPlayNetworking.send(SpyglassHotkeyServer.SWAPPING_ID, PacketByteBufs.create()
                                .writeItemStack(spyglassStackInfo));
                        ClientPlayNetworking.send(SpyglassHotkeyServer.SWAPPING_ID, PacketByteBufs.create()
                                .writeItemStack(handStackInfo));
                    } else {
                        spyglassSlot = client.player.getInventory().getSlotWithStack(new ItemStack(Items.SPYGLASS));
                        currentSlot = client.player.getInventory().selectedSlot;

                        if (spyglassSlot == -1) {
                            continue;
                        }

                        spyglassStackInfo = client.player.getInventory().getStack(spyglassSlot);
                        handStackInfo = client.player.getInventory().getStack(currentSlot);

                        client.player.setStackInHand(Hand.MAIN_HAND, spyglassStackInfo);

                        ClientPlayNetworking.send(SpyglassHotkeyServer.SWAPPING_ID, PacketByteBufs.create()
                                                                                    .writeItemStack(spyglassStackInfo));

                        client.player.getInventory().setStack(spyglassSlot, handStackInfo);
                        ClientPlayNetworking.send(SpyglassHotkeyServer.SWAPPING_ID, PacketByteBufs.create()
                                                                                    .writeItemStack(handStackInfo));
                    }
                }
            }
        });
    }
}
