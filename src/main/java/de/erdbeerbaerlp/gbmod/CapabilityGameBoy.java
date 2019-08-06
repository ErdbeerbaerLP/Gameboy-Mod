package de.erdbeerbaerlp.gbmod;

import eu.rekawek.coffeegb.gui.Emulator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public class CapabilityGameBoy implements IGameboy, ICapabilitySerializable<NBTTagCompound> {
    private final Emulator emu = new Emulator();
    private int romIndex = -1;
    private ROM rom;
    @Override
    public Emulator getEmulator() {
        return emu;
    }

    @Override
    public int getRomIndex() {
        return this.romIndex;
    }

    @Override
    public ROM getRom() {
        return rom;
    }

    @Override
    public void setRom(int rom) {
        if (rom >= Gbmod.LOADED_ROMS.size())
            rom = 0;
        this.romIndex = rom;
        this.rom = Gbmod.LOADED_ROMS.get(romIndex);
        try {
            this.emu.switchRom(this.rom.getRomFile(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextRom() {
        romIndex++;
        if (romIndex >= Gbmod.LOADED_ROMS.size())
            romIndex = 0;
        this.rom = Gbmod.LOADED_ROMS.get(romIndex);
        try {
            this.emu.switchRom(rom.getRomFile(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("romIndex", this.romIndex);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.setRom(nbt.getInteger("romIndex"));
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == Gbmod.CAP_GB;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == Gbmod.CAP_GB)
            return Gbmod.CAP_GB.cast(this);
        return null;
    }

    public static class CapabilityGameBoyStorage implements Capability.IStorage<IGameboy> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IGameboy> capability, IGameboy instance, EnumFacing side) {
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("romIndex", instance.getRomIndex());
            return nbt;
        }


        @Override
        public void readNBT(Capability<IGameboy> capability, IGameboy instance, EnumFacing side, NBTBase nbt) {
            instance.setRom(((NBTTagCompound) nbt).getInteger("romIndex"));
        }
    }
}
