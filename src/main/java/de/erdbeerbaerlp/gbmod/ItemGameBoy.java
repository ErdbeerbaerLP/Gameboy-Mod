package de.erdbeerbaerlp.gbmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public class ItemGameBoy extends Item {
    public ItemGameBoy() {
        setRegistryName(Gbmod.MOD_ID, "gameboy");
        setTranslationKey("gameboy");
        setMaxStackSize(1);
    }

    public static CapabilityGameBoy getCap(ItemStack is) {
        return (CapabilityGameBoy) is.getCapability(Gbmod.CAP_GB, null);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new CapabilityGameBoy();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        if(worldIn.isRemote){
            final CapabilityGameBoy cap = getCap(playerIn.getHeldItem(handIn));
            System.out.println(cap);
            if(Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {
                cap.nextRom();
                try {
                    cap.getEmulator().switchRom(cap.getRom().getRomFile(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Selected ROM " + cap.getRom().getRomName()));

            }else
                Minecraft.getMinecraft().displayGuiScreen(new GuiGameboy(cap));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        final CapabilityGameBoy cap = getCap(stack);
        tooltip.add(I18n.format("item.gameboy.tooltip.1", flagIn.isAdvanced() ? cap.getRom().getRomFile().getName() : cap.getRom().getRomName()));
        if (flagIn.isAdvanced()) {
            tooltip.add(I18n.format("item.gameboy.tooltip.2", cap.getRomIndex()));
        }
    }
}