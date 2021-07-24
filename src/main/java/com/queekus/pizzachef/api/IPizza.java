package com.queekus.pizzachef.api;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public interface IPizza
{
    boolean canModifyIngredients();
    int getAvailableIngredientSlots();
    boolean isIngredientValid(ItemStack potentialIngredient);

    static IItemHandler getHandlerForPizza(ItemStack stack)
    {
        return new PizzaInventoryHandler(stack);
    }
}
