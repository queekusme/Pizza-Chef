package com.queekus.pizzachef.crafting;

import net.minecraft.world.Container;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmokingRecipe;

public class TransferNBTSmokerRecipe extends SmokingRecipe
{
    public TransferNBTSmokerRecipe(
        ResourceLocation id, String group, Ingredient ingredient,
        ItemStack result, float experience, int cookingTime
    )
    {
        super(id, group, ingredient, result, experience, cookingTime);
    }

    @Override // Requires at least Forge: 1.16.5-36.1.51
    public ItemStack assemble(IInventory craftingInv)
    {
        ItemStack result = super.assemble(craftingInv);

        NBTCrafting.transferNBT(result, craftingInv.getItem(0));

        return result;
    }
}
