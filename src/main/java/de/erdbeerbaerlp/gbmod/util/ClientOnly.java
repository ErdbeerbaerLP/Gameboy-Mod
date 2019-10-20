package de.erdbeerbaerlp.gbmod.util;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class ClientOnly {
    /*
    Key Bindings
     */
    public static final KeyBinding keyGBLeft = new KeyBinding("key.gb.left", Keyboard.KEY_LEFT, "category.gameboy");

    public static final KeyBinding keyGBRight = new KeyBinding("key.gb.right", Keyboard.KEY_RIGHT, "category.gameboy");

    public static final KeyBinding keyGBUp = new KeyBinding("key.gb.up", Keyboard.KEY_UP, "category.gameboy");

    public static final KeyBinding keyGBDown = new KeyBinding("key.gb.down", Keyboard.KEY_DOWN, "category.gameboy");

    public static final KeyBinding keyGBA = new KeyBinding("key.gb.a", Keyboard.KEY_NUMPAD1, "category.gameboy");

    public static final KeyBinding keyGBB = new KeyBinding("key.gb.b", Keyboard.KEY_NUMPAD0, "category.gameboy");

    public static final KeyBinding keyGBStart = new KeyBinding("key.gb.start", Keyboard.KEY_RETURN, "category.gameboy");

    public static final KeyBinding keyGBSelect = new KeyBinding("key.gb.select", Keyboard.KEY_RSHIFT, "category.gameboy");

    public static void registerKeybinds() {
        ClientRegistry.registerKeyBinding(keyGBA);
        ClientRegistry.registerKeyBinding(keyGBB);
        ClientRegistry.registerKeyBinding(keyGBDown);
        ClientRegistry.registerKeyBinding(keyGBUp);
        ClientRegistry.registerKeyBinding(keyGBLeft);
        ClientRegistry.registerKeyBinding(keyGBRight);
        ClientRegistry.registerKeyBinding(keyGBStart);
        ClientRegistry.registerKeyBinding(keyGBSelect);
    }
}
