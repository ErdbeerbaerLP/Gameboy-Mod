package de.erdbeerbaerlp.gbmod;

import de.erdbeerbaerlp.gbmod.items.ItemGameBoy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload e) {
        Gbmod.unloadAllEmus();
    }

    @SubscribeEvent
    public static void onKey(TickEvent.ClientTickEvent ev) {
        if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindDrop.getKeyCode())) {
            if (Minecraft.getMinecraft().currentScreen == null) {
                if (Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemGameBoy) {
                    ItemGameBoy.getCap(Minecraft.getMinecraft().player.getHeldItemMainhand()).getEmulator().stop();
                    Minecraft.getMinecraft().player.dropItem(Minecraft.getMinecraft().player.getHeldItemMainhand(), false);
                }
            } else if (Minecraft.getMinecraft().currentScreen instanceof GuiContainer) {
                if (((GuiContainer) Minecraft.getMinecraft().currentScreen).getSlotUnderMouse() != null) {
                    @SuppressWarnings("ConstantConditions") ItemStack i = ((GuiContainer) Minecraft.getMinecraft().currentScreen).getSlotUnderMouse().getStack();
                    if (i != ItemStack.EMPTY && i.getItem() instanceof ItemGameBoy)
                        if (ItemGameBoy.getCap(i).hasEmulator())
                            ItemGameBoy.getCap(i).getEmulator().stop();
                }
            }
        }
    }
}