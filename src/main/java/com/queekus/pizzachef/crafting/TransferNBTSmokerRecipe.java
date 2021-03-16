package com.queekus.pizzachef.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmokingRecipe;
import net.minecraft.util.ResourceLocation;

public class TransferNBTSmokerRecipe extends SmokingRecipe
{
    public TransferNBTSmokerRecipe(
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
