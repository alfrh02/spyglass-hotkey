package land.alfred.spyglasshotkey.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Keyboard;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class SpyglassHotkeyClient implements ClientModInitializer {

    public static final String MODID = "spyglass_hotkey";
    public static KeyBinding spyglassKeybind;
    public int spyglassSlot = -1;
    public int currentSlot = -1;
    public boolean pressed;
    public boolean spyglassed;
    public boolean released;
    public ItemStack spyglassItem = new ItemStack(Items.SPYGLASS);

    @Override
    public void onInitializeClient() {
        spyglassKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key." + MODID + ".spyglass",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "category." + MODID + ".keybind"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            /*
            if (spyglassKeybind.wasPressed()) {
                spyglassed = false;
                toggle = !toggle;
                System.out.println("toggle " + toggle);
            }
            if (toggle) {
                if (client.player.getInventory().contains(spyglassItem) && client.player.getOffHandStack() != spyglassItem && client.player.getInventory().getMainHandStack() != spyglassItem) {
                    spyglassSlot = client.player.getInventory().getSlotWithStack(spyglassItem);
                    currentSlot = client.player.getInventory().selectedSlot;
                    client.interactionManager.pickFromInventory(spyglassSlot);
                    spyglassed = true;
                }
            }
            if (spyglassed) {
                if (!toggle) {
                    client.interactionManager.pickFromInventory(currentSlot);
                    spyglassed = false;
                } else {
                    toggle = false;
                    client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                }
            }
             */
            while (spyglassKeybind.wasPressed()) {
                spyglassed = false;
                if (client.player.getInventory().contains(new ItemStack(Items.SPYGLASS)) && client.player.getOffHandStack().getItem() != Items.SPYGLASS) {
                    if (client.player.getInventory().getStack(client.player.getInventory().selectedSlot).getItem() == Items.SPYGLASS && currentSlot != -1) {
                        client.interactionManager.pickFromInventory(spyglassSlot);
                    } else {
                        spyglassSlot = client.player.getInventory().getSlotWithStack(new ItemStack(Items.SPYGLASS));
                        currentSlot = client.player.getInventory().selectedSlot;
                        if (spyglassSlot != -1) {
                            client.interactionManager.pickFromInventory(spyglassSlot);
                            spyglassed = true;
                        }
                    }
                }
            }
            if (spyglassed) {
                spyglassItem.use(client.world, client.player, Hand.MAIN_HAND);
                spyglassed = false;
            }
        });
    }
}
