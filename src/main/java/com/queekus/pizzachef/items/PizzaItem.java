package com.queekus.pizzachef.items;

import java.util.List;

import javax.annotation.Nullable;

import com.queekus.pizzachef.api.IPizza;
import com.queekus.pizzachef.data.tags.ModTags;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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

    @Override
    public boolean isEdible()
    {
        return this.asItem() == ModItems.pizza_slice;
    }

    @Override
    public Food getFoodProperties()
    {
        /**
         * NOTE: Currently Impossible to generate desired values from Items within Pizza's NBT
         * This is due to https://github.com/MinecraftForge/MinecraftForge/issues/7701
         */
        return new Food.Builder()
            .nutrition(0)
            .saturationMod(0)
            .build();
    }

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand hand)
    {
        if (this.isEdible())
        {
            ItemStack itemstack = player.getItemInHand(hand);
            if (player.canEat(this.getFoodProperties().canAlwaysEat()))
            {
                player.startUsingItem(hand);
                return ActionResult.consume(itemstack);
            }

            return ActionResult.fail(itemstack);
        }

        return ActionResult.pass(player.getItemInHand(hand));
    }
}
