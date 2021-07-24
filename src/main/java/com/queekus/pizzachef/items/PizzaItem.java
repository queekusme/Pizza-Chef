package com.queekus.pizzachef.items;

import java.util.List;

import javax.annotation.Nullable;

import com.queekus.pizzachef.api.IPizza;
import com.queekus.pizzachef.data.tags.ModTags;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        IItemHandler handler = IPizza.getHandlerForPizza(stack);
        boolean isSlice = this == ModItems.pizza_slice;
        String ingredientIndent = !isSlice ? "  " : "";

        for(int i = handler.getSlots() - 1; i >= 0; i--)
        {
            ItemStack slotStack = handler.getStackInSlot(i);

            if(!isSlice)
            {
                if(i == handler.getSlots() - 1)
                    tooltip.add(new TextComponent(ChatFormatting.GRAY + "- " + ChatFormatting.RESET + I18n.get("pizza.lore.left") + ":"));
                else if(i == (handler.getSlots() / 2) - 1)
                    tooltip.add(new TextComponent(ChatFormatting.GRAY + "- " + ChatFormatting.RESET + I18n.get("pizza.lore.right") + ":"));
            }

            if(slotStack != ItemStack.EMPTY)
                tooltip.add(new TextComponent(ChatFormatting.AQUA + ingredientIndent + "- " + I18n.get(slotStack.getItem().getDescriptionId())));
        }
    }

    @Override
    public boolean isEdible()
    {
        return this.asItem() == ModItems.pizza_slice;
    }

    @Override
    public FoodProperties getFoodProperties()
    {
        /**
         * NOTE: Currently Impossible to generate desired values from Items within Pizza's NBT
         * This is due to https://github.com/MinecraftForge/MinecraftForge/issues/7701
         */
        return new FoodProperties.Builder()
            .nutrition(0)
            .saturationMod(0)
            .build();
    }
}
