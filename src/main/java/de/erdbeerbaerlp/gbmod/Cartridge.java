package de.erdbeerbaerlp.gbmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class Cartridge extends Item {
    @SuppressWarnings("FieldCanBeLocal")
    private ROM rom;
    private int index = 0;
    public Cartridge() {
        setRegistryName("modid", "cartridge");
        setTranslationKey("cartridge-unknown");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(Gbmod.LOADED_ROMS.size() < index)
            index++;
        else index = 0;
        this.rom = Gbmod.LOADED_ROMS.get(index);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}