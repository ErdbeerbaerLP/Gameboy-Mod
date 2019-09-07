package de.erdbeerbaerlp.gbmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabGameboy extends CreativeTabs {
    public TabGameboy() {
        super("tabGameboy");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Gbmod.Items.itemGameBoy);
    }
}
