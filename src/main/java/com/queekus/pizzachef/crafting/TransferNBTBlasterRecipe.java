package com.queekus.pizzachef.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class TransferNBTBlasterRecipe extends BlastingRecipe
{
    public TransferNBTBlasterRecipe(
        ResourceLocation id, String group, Ingredient ingredient,
        ItemStack result, float experience, int cookingTime
    )
    {
        super(id, group, ingredient, result, experience, cookingTime);
    }

    @Override // Currently non-operational due to AbstractFurnaceTileEntity::burn using IRecipe::getResultItem rather than IRecipe::assemble
    public ItemStack assemble(IInventory craftingInv)
    {
        ItemStack result = super.assemble(craftingInv);

        NBTCrafting.transferNBT(result, craftingInv.getItem(0));

        return result;
    }
}