package land.alfred.spyglasshotkey;

import land.alfred.spyglasshotkey.server.SpyglassHotkeyServer;
import net.fabricmc.api.ModInitializer;

public class SpyglassHotkey implements ModInitializer {

    public static final String MODID = "spyglass_hotkey";

    public void onInitialize() {
        System.out.println("Spyglass Hotkey Initialised");
        SpyglassHotkeyServer.registerPackets();
    }
}
