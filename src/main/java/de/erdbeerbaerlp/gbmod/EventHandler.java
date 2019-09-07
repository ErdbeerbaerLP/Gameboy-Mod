package de.erdbeerbaerlp.gbmod;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload e) {
        Gbmod.unloadAllEmus();
    }
}