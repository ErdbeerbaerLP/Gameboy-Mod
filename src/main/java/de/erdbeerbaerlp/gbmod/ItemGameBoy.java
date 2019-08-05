package de.erdbeerbaerlp.gbmod;

import eu.rekawek.coffeegb.gui.Emulator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public class ItemGameBoy extends Item implements  ICapabilityProvider{
    ROM rom;
    CapabilityGameBoy cap;
    public ItemGameBoy() {
        cap = new CapabilityGameBoy();
        setRegistryName(Gbmod.MOD_ID, "gameboy");
        setTranslationKey("gameboy");
        setMaxStackSize(1);
    }
    public Emulator getEmu(){
        return cap.getEmulator();
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(worldIn.isRemote){
            if(Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {
                cap.setRom(cap.getRom()+1);
                if (cap.getRom() >= Gbmod.LOADED_ROMS.size())
                    cap.setRom(0);
                this.rom = Gbmod.LOADED_ROMS.get(cap.getRom());
                try {
                    cap.getEmulator().switchRom(rom.getRomFile(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Selected ROM "+rom.getRomName()));

            }else
                Minecraft.getMinecraft().displayGuiScreen(new GuiGameboy(this));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == Gbmod.CAP_GB;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == Gbmod.CAP_GB) return (T) cap;
        return null;
    }
}