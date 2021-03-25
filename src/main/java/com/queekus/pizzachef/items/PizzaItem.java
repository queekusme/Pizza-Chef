package com.queekus.pizzachef.items;

import java.util.List;

import javax.annotation.Nullable;

import com.queekus.pizzachef.api.IPizza;
import com.queekus.pizzachef.data.tags.ModTags;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;

public class PizzaItem extends Item implements IPizza
{
    public PizzaItem(Properties props)
    {
        super(props);
    }

    public boolean canModifyIngredients()
    {
        return this == ModItems.pizza_base;
    }

    @Override
    public int getAvailableIngredientSlots()
    {
        if(this == ModItems.pizza_slice)
            return 4; // Slice is 1/8th of a pizza and so depends on the side it was from

        return 8; // 4x slots per side 0-3 is left, 4-7 is right
    }

    @Override
    public boolean isIngredientValid(ItemStack potentialIngredient)
    {
        // Only allow items included in the ingredients tag
        return ModTags.Items.PIZZA_INGREDIENTS.contains(potentialIngredient.getItem());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        IItemHandler handler = IPizza.getHandlerForPizza(stack);
        Item pizzaItem = this.getItem();
        boolean isSlice = pizzaItem == ModItems.pizza_slice;
        String ingredientIndent = !isSlice ? "  " : "";

        for(int i = handler.getSlots() - 1; i >= 0; i--)
        {
            ItemStack slotStack = handler.getStackInSlot(i);

            if(!isSlice)
            {
                if(i == handler.getSlots() - 1)
                    tooltip.add(new StringTextComponent(TextFormatting.GRAY + "- " + TextFormatting.RESET + I18n.get("pizza.lore.left") + ":"));
                else if(i == (handler.getSlots() / 2) - 1)
                    tooltip.add(new StringTextComponent(TextFormatting.GRAY + "- " + TextFormatting.RESET + I18n.get("pizza.lore.right") + ":"));
            }

            if(slotStack != ItemStack.EMPTY)
                tooltip.add(new StringTextComponent(TextFormatting.AQUA + ingredientIndent + "- " + I18n.get(slotStack.getItem().getDescriptionId())));
        }
    }
}
