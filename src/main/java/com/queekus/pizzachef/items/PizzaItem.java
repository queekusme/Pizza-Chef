package com.queekus.pizzachef.items;

import java.util.List;

import javax.annotation.Nullable;

import com.queekus.pizzachef.data.tags.ModTags;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;

public class PizzaItem extends Item
{
    public PizzaItem(Properties props)
    {
        super(props);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        IItemHandler handler = PizzaItem.getHandlerForPizza(stack);
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

    public static IItemHandler getHandlerForPizza(ItemStack stack)
    {
        return new PizzaInventoryHandler(stack);
    }

    public static class PizzaInventoryHandler implements IItemHandler
    {
        private static final String INVENTORY_KEY = "inventory_";
        private ItemStack workingStack;

        public PizzaInventoryHandler(ItemStack currentPizza)
        {
            this.workingStack = currentPizza;
        }

        @Override
        public int getSlots()
        {
            if(this.workingStack.getItem() == ModItems.pizza_slice)
                return 4; // Slice is 1/8th of a pizza and so depends on the side it was from

            return 8; // 4x slots per side 0-3 is left, 4-7 is right
        }

        @Override
        public ItemStack getStackInSlot(int slot)
        {
            CompoundNBT pizzaTag = this.getPizzaCompound();

            if(!pizzaTag.contains(this.getKeyForSlot(slot)))
                return ItemStack.EMPTY;

            CompoundNBT stackAsTag = pizzaTag.getCompound(this.getKeyForSlot(slot));

            return ItemStack.of(stackAsTag);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            if(!this.canInsertInSlot(slot))
                return stack;

            if(stack == ItemStack.EMPTY)
                return ItemStack.EMPTY;

            boolean tagMatches = ModTags.Items.PIZZA_INGREDIENTS.contains(stack.getItem());
            if(!tagMatches)
                return stack;

            if(!simulate)
            {
                stack.copy().setCount(this.getSlotLimit(slot));

                CompoundNBT stackAsTag = stack.save(new CompoundNBT());
                CompoundNBT pizzaTag = this.getPizzaCompound();

                pizzaTag.put(this.getKeyForSlot(slot), stackAsTag);
            }

            ItemStack returnStack = stack.copy();
            returnStack.shrink(this.getSlotLimit(slot));

            return returnStack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            if(!this.canExtractFromSlot(slot) || amount == 0)
                return ItemStack.EMPTY;

            CompoundNBT pizzaTag = this.getPizzaCompound();
            CompoundNBT stackAsTag = pizzaTag.getCompound(this.getKeyForSlot(slot));

            pizzaTag.remove(this.getKeyForSlot(slot));

            return ItemStack.of(stackAsTag);
        }

        private String getKeyForSlot(int slot) {
            return INVENTORY_KEY + slot;
        }

        @Override
        public int getSlotLimit(int slot)
        {
            return 1; // 1 Ingredient per slot
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            // Only allow items included in the ingredients tag
            return ModTags.Items.PIZZA_INGREDIENTS.contains(stack.getItem());
        }

        public NonNullList<ItemStack> getAllItems()
        {
            CompoundNBT pizzaTag = this.getPizzaCompound();
            NonNullList<ItemStack> allSlots = NonNullList.create();

            for(int i = 0; i < this.getSlots(); i++)
                allSlots.add(ItemStack.of(pizzaTag.getCompound(this.getKeyForSlot(i))));

            allSlots.removeIf((itemStack) -> itemStack.getCount() == 0);

            return allSlots;
        }

        private boolean canInsertInSlot(int slot)
        {
            CompoundNBT pizzaTag = this.getPizzaCompound();

            return !pizzaTag.contains(this.getKeyForSlot(slot));
        }

        private boolean canExtractFromSlot(int slot)
        {
            CompoundNBT pizzaTag = this.getPizzaCompound();

            return pizzaTag.contains(this.getKeyForSlot(slot));
        }

        private CompoundNBT getPizzaCompound()
        {
            return workingStack.getOrCreateTag();
        }
    }
}
