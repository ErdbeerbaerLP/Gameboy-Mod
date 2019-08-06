package de.erdbeerbaerlp.gbmod;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod(
        modid = Gbmod.MOD_ID,
        name = Gbmod.MOD_NAME,
        version = Gbmod.VERSION,
        dependencies = "required-after-client:eguilib@0.1.0"
)
public class Gbmod {

    public static final String MOD_ID = "gbmod";
    public static final String MOD_NAME = "GameBoy Mod";
    public static final String VERSION = "1.0.0";
    private static final File ROMS_DIR = new File("./config/gameboy-roms");
    static final List<ROM> LOADED_ROMS = new ArrayList<>();

    public Gbmod(){
        reloadRoms();
    }

    /**
     * Reloads the rom directory...
     */
    public static void reloadRoms() {
        if(!ROMS_DIR.exists()) //noinspection ResultOfMethodCallIgnored
            ROMS_DIR.mkdirs();
        System.out.println("Loading ROMs...");
        LOADED_ROMS.clear();
        try {
            for(String file : Objects.requireNonNull(ROMS_DIR.list())){
                final File romFile = new File(ROMS_DIR, file);
                if(!romFile.isDirectory()){
                    final String ext = FilenameUtils.getExtension(romFile.getName());
                    if(ext.equals("gb")||ext.equals("gbc")||ext.equals("rom"))
                        LOADED_ROMS.add(new ROM(romFile));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Loaded "+LOADED_ROMS.size()+" ROMs");
    }

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static Gbmod INSTANCE;

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

    //Capabilities
    @CapabilityInject(IGameboy.class)
    public static Capability<IGameboy> CAP_GB = null;
    public static final ResourceLocation CAP_GB_RL = new ResourceLocation(MOD_ID, "capability.gameboy");

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        ClientRegistry.registerKeyBinding(keyGBA);
        ClientRegistry.registerKeyBinding(keyGBB);
        ClientRegistry.registerKeyBinding(keyGBDown);
        ClientRegistry.registerKeyBinding(keyGBUp);
        ClientRegistry.registerKeyBinding(keyGBLeft);
        ClientRegistry.registerKeyBinding(keyGBRight);
        ClientRegistry.registerKeyBinding(keyGBStart);
        ClientRegistry.registerKeyBinding(keyGBSelect);
        //noinspection deprecation
        CapabilityManager.INSTANCE.register(IGameboy.class, new CapabilityGameBoy.CapabilityGameBoyStorage(), CapabilityGameBoy.class);
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    /**
     * Forge will automatically look up and bind blocks to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks {
      /*
          public static final MySpecialBlock mySpecialBlock = null; // placeholder for special block below
      */
    }

    /**
     * Forge will automatically look up and bind items to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Items {
        public static final ItemGameBoy itemGameBoy = null;
        public static final Cartridge cartridge = null;
    }

    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     */
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        /**
         * Listen for the register event for creating custom items
         */
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(new ItemGameBoy(), new Cartridge());
        }

        /**
         * Listen for the register event for creating custom blocks
         */
        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
        }

    }
}
