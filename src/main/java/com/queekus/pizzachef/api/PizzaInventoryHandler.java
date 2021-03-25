package com.queekus.pizzachef.api;

import com.queekus.pizzachef.data.tags.ModTags;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;

public class PizzaInventoryHandler implements IItemHandler
{
    private static final String INVENTORY_KEY = "inventory_";
    private ItemStack workingStack;

    public PizzaInventoryHandler(ItemStack currentPizza)
    {
        if(!(currentPizza.getItem() instanceof IPizza))
            throw new RuntimeException(String.format("Cannot handle item %s as a Pizza, it doesn't inherit from IPizza", currentPizza.getItem().getRegistryName()));

        this.workingStack = currentPizza;
    }

    /**
     * Removes the side not desired
     *
     * e.g. I...II.I == I........ or ....II.I
     *
     * @param side
     * @return current handler
     */
    public PizzaInventoryHandler reduceToSide(PizzaSide side)
    {
        for(int i = 0; i < this.getSlots(); i++)
        {
            if((side == PizzaSide.LEFT && i >= this.getSlots() / 2) || (side == PizzaSide.RIGHT && i < this.getSlots() / 2))
                this.forgetItem(i);
        }

        return this;
    }

    /**
     * Reduces the item down so that there are no spaces between items
     * e.g. I...II.I == IIII....
     *
     * @return current handler
     */
    public PizzaInventoryHandler defrag()
    {
        int slot = 0;
        int firstEmptySlot = -1;
        while(slot < this.getSlots())
        {
            if(this.getStackInSlot(slot) == ItemStack.EMPTY)
            {
                if(firstEmptySlot == -1)
                    firstEmptySlot = slot;

                slot++;
                continue;
            }

            if(firstEmptySlot == -1)
            {
                slot++;
                continue;
            }

            this.insertItem(firstEmptySlot, this.extractItem(slot, 1, false, true), false, true);
            slot = firstEmptySlot;
            firstEmptySlot = -1;
        }

        return this;
    }

    private IPizza asIPizza()
    {
        return ((IPizza)workingStack.getItem());
    }

    @Override
    public int getSlots()
    {
        return this.asIPizza().getAvailableIngredientSlots();
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
        return this.insertItem(slot, stack, simulate, false);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return this.extractItem(slot, amount, simulate, false);
    }

    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate, boolean force)
    {
        if(!force && !this.canInsertInSlot(slot))
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

    public ItemStack extractItem(int slot, int amount, boolean simulate, boolean force)
    {
        if((!force && !this.canExtractFromSlot(slot)) || amount == 0)
            return ItemStack.EMPTY;

        CompoundNBT pizzaTag = this.getPizzaCompound();
        CompoundNBT stackAsTag = pizzaTag.getCompound(this.getKeyForSlot(slot));

        if(!simulate)
            pizzaTag.remove(this.getKeyForSlot(slot));

        return ItemStack.of(stackAsTag);
    }

    public void forgetItem(int slot)
    {
        this.getPizzaCompound().remove(this.getKeyForSlot(slot));
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
        return this.asIPizza().isIngredientValid(stack);
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
        return this.asIPizza().canModifyIngredients() && !this.getPizzaCompound().contains(this.getKeyForSlot(slot));
    }

    private boolean canExtractFromSlot(int slot)
    {
        return this.asIPizza().canModifyIngredients() && this.getPizzaCompound().contains(this.getKeyForSlot(slot));
    }

    private CompoundNBT getPizzaCompound()
    {
        return workingStack.getOrCreateTag();
    }
}