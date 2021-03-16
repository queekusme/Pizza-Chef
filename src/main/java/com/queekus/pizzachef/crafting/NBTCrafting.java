package com.queekus.pizzachef.crafting;

import net.minecraft.item.ItemStack;

public class NBTCrafting
{
    public static void transferNBT(ItemStack result, ItemStack input)
    {
        if(input.hasTag())
            result.setTag(input.getTag().copy());
    }
}
