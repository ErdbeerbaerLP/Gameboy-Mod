package de.erdbeerbaerlp.gbmod.items;

import de.erdbeerbaerlp.gbmod.Gbmod;
import de.erdbeerbaerlp.gbmod.util.ROM;
import eu.rekawek.coffeegb.gui.Emulator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public class CapabilityGameBoy implements IGameBoy, ICapabilitySerializable<NBTTagCompound> {
    private Emulator emu;
    private int romIndex = -1;
    private ROM rom;

    public void initEmulator() {
        emu = new Emulator(this);
        Gbmod.emus.add(this);
    }
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
        if (FMLCommonHandler.instance().getSide().isServer()) return;
        if (rom >= Gbmod.LOADED_ROMS.size() || rom < 0)
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
        if (FMLCommonHandler.instance().getSide().isServer()) return;
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
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
            this.initEmulator();
        this.setRom(nbt.getInteger("romIndex"));
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == Gbmod.CAP_CART;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == Gbmod.CAP_CART)
            return Gbmod.CAP_CART.cast(this);
        return null;
    }


    public static class CapabilityCartridgeStorage implements Capability.IStorage<IGameBoy> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IGameBoy> capability, IGameBoy instance, EnumFacing side) {
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("romIndex", instance.getRomIndex());
            return nbt;
        }


        @Override
        public void readNBT(Capability<IGameBoy> capability, IGameBoy instance, EnumFacing side, NBTBase nbt) {
            if (FMLCommonHandler.instance().getSide().isClient())
                instance.initEmulator();
            instance.setRom(((NBTTagCompound) nbt).getInteger("romIndex"));
        }
    }
}
