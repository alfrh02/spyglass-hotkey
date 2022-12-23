package land.alfred.spyglasshotkey.server;

import land.alfred.spyglasshotkey.SpyglassHotkey;
import land.alfred.spyglasshotkey.server.packet.SpyglassHotkeyPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class SpyglassHotkeyServer {
    public static final Identifier SWAPPING_ID = new Identifier(SpyglassHotkey.MODID, "swapping");

    public static void registerPackets() {
        System.out.println("Registering Spyglass Hotkey Packets!");
        ServerPlayNetworking.registerGlobalReceiver(SWAPPING_ID, SpyglassHotkeyPacket::receive);
    }
}
