package com.queekus.pizzachef.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingRetainedItem extends Item
{
    public CraftingRetainedItem(Properties props)
    {
        super(props);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack copy = itemStack.copy();

        if(itemStack.isDamageableItem())
        {
            copy.setDamageValue(copy.getDamageValue() + 1);

            int damage = copy.getMaxDamage() - copy.getDamageValue();
            if(damage <= 0)
                return ItemStack.EMPTY;
        }

        return copy;
    }
}
