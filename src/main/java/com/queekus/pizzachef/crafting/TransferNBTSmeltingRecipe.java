package com.queekus.pizzachef.crafting;

import net.minecraft.world.Container;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.Ingredient;

public class TransferNBTSmeltingRecipe extends SmeltingRecipe
{
    public TransferNBTSmeltingRecipe(
        ResourceLocation id, String group, Ingredient ingredient,
        ItemStack result, float experience, int cookingTime
    )
    {
        super(id, group, ingredient, result, experience, cookingTime);
    }

    @Override
    public ItemStack assemble(Container craftingInv)
    {
        ItemStack result = super.assemble(craftingInv);

        NBTCrafting.transferNBT(result, craftingInv.getItem(0));

        return result;
    }
}
