package de.erdbeerbaerlp.gbmod;

import eu.rekawek.coffeegb.gui.Emulator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class CapabilityGameBoy implements IGameboy {
    private final Emulator emu = new Emulator();
    private int rom = -1;
    @Override
    public Emulator getEmulator() {
        return emu;
    }

    @Override
    public int getRom() {
        return this.rom;
    }

    @Override
    public void setRom(int rom) {
        this.rom = rom;
    }

    public static class CapabilityGameBoyStorage implements Capability.IStorage<IGameboy> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IGameboy> capability, IGameboy instance, EnumFacing side) {
            return new NBTTagCompound();
        }


        @Override
        public void readNBT(Capability<IGameboy> capability, IGameboy instance, EnumFacing side, NBTBase nbt) {}
    }
}
