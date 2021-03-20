package com.queekus.pizzachef.tileentities;

import java.util.Arrays;

import javax.annotation.Nullable;

import com.queekus.pizzachef.api.IPizza;
import com.queekus.pizzachef.api.PizzaInventoryHandler;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;

public class TileEntityGranitePizzaSlab extends LockableTileEntity implements ISidedInventory
{
    private static final int PIZZA_SLOT = 0;

    private static final int LEFT_0 = 1;
    private static final int LEFT_1 = 2;
    private static final int LEFT_2 = 3;
    private static final int LEFT_3 = 4;

    private static final int RIGHT_0 = 5;
    private static final int RIGHT_1 = 6;
    private static final int RIGHT_2 = 7;
    private static final int RIGHT_3 = 8;

    private static final int[] SLOTS_UP = new int[]{ PIZZA_SLOT };
    private static final int[] SLOTS_DOWN = new int[]{ PIZZA_SLOT };
    private static final int[] SLOTS_LEFT = new int[]{ LEFT_0, LEFT_1, LEFT_2, LEFT_3 };
    private static final int[] SLOTS_RIGHT = new int[]{ RIGHT_0, RIGHT_1, RIGHT_2, RIGHT_3 };

    protected NonNullList<ItemStack> pizzStack = NonNullList.withSize(1, ItemStack.EMPTY);

    public TileEntityGranitePizzaSlab()
    {
        super(ModTileEntities.GRANITE_PIZZA_SLAB.get());
    }

    public ItemStack getPizza()
    {
        return this.pizzStack.get(PIZZA_SLOT);
    }

    public boolean hasPizza()
    {
        return !this.getPizza().isEmpty();
    }

    public PizzaInventoryHandler getPizzaHandler()
    {
        return (PizzaInventoryHandler) IPizza.getHandlerForPizza(this.getPizza());
    }

    @Override
    public int getContainerSize()
    {
        int internalPizzaSlots = this.hasPizza() ? this.getPizzaHandler().getSlots() : 0;

        return internalPizzaSlots + 1; // + 1 for pizza
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt)
    {
        super.load(state, nbt);

        this.pizzStack.clear(); // remove current and replace with new

        ItemStackHelper.loadAllItems(nbt, this.pizzStack);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound)
    {
        super.save(compound);

        return ItemStackHelper.saveAllItems(compound, this.pizzStack);
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing)
    {
        if (!this.isRemoved() && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else if (facing == Direction.EAST)
                return handlers[2].cast();
            else if (facing == Direction.WEST)
                return handlers[3].cast();
        }

        return LazyOptional.empty();
    }

    public NonNullList<ItemStack> getAllItems()
    {
        NonNullList<ItemStack> slots = NonNullList.create();

        if(this.hasPizza())
        {
            slots.add(this.getPizza());
            slots.addAll(this.getPizzaHandler().getAllItems());
        }

        return slots;
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

    @Override
    public boolean isEmpty()
    {
        return !this.hasPizza();
    }

    @Override
    public ItemStack getItem(int index)
    {
        if (this.hasPizza())
        {
            if(index == PIZZA_SLOT)
                return this.getPizza();
            else
                this.getPizzaHandler().getStackInSlot(index - 1); // -1 for Pizza Slot
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int index, int count)
    {
        if (this.hasPizza())
        {
            ItemStack itemStack;
            if(index == PIZZA_SLOT)
                itemStack = ItemStackHelper.takeItem(this.pizzStack, index); // Ignore count as we only handle 1 per slot
            else
                itemStack = this.getPizzaHandler().extractItem(index - 1, count, false); // -1 for Pizza Slot

            if(!itemStack.isEmpty())
                this.setChanged();

            return itemStack;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index)
    {
        if (this.hasPizza())
        {
            if(index == PIZZA_SLOT)
                return ItemStackHelper.takeItem(this.pizzStack, index);
            else
                this.getPizzaHandler().extractItem(index - 1, 1, false); // -1 for Pizza Slot
        }

        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        if(index == PIZZA_SLOT)
        {
            if (this.getPizza().isEmpty())
            {
                this.pizzStack.set(0, stack);
                this.setChanged();
            }
        }
        else if(this.hasPizza())
        {
            if(this.getPizzaHandler().insertItem(index - 1, stack, false).isEmpty()) // -1 for Pizza Slot
                this.setChanged();
        }
    }

    @Override
    public boolean stillValid(PlayerEntity player)
    {
       if (this.level.getBlockEntity(this.worldPosition) != this)
            return false;
       else
       {
            return player.distanceToSqr(
                (double)this.worldPosition.getX() + 0.5D,
                (double)this.worldPosition.getY() + 0.5D,
                (double)this.worldPosition.getZ() + 0.5D
            ) <= 64.0D;
       }
    }

    @Override
    public void clearContent()
    {
        this.pizzStack.clear();
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        int[] NONE = new int[]{};

        switch(direction)
        {
            case UP: return SLOTS_UP;
            case DOWN: return SLOTS_DOWN;
            case EAST: return SLOTS_LEFT;
            case WEST: return SLOTS_RIGHT;
            default: return NONE;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction)
    {
        if(Arrays.stream(this.getSlotsForFace(direction)).anyMatch(i -> i == index)) // Slot must be assigned to side to insert
            return this.canPlaceItem(index, itemStackIn); // Then verify item specifically can be assigned

        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction)
    {
        if(Arrays.stream(this.getSlotsForFace(direction)).anyMatch(i -> i == index)) // Slot must be assigned to side to extract
            return this.canTakeItem(index, stack);// Then verify item specifically can be removed

        return false;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack)
    {
        if (index == PIZZA_SLOT)
            return !this.hasPizza() && stack.getItem() instanceof IPizza;

        return this.hasPizza() && this.getPizzaHandler().insertItem(index - 1, stack, true).getCount() == ItemStack.EMPTY.getCount();  // -1 for Pizza Slot
    }

    public boolean canTakeItem(int index, ItemStack stack)
    {
        if (index == PIZZA_SLOT)
            return this.hasPizza();

        return this.hasPizza() && this.getPizzaHandler().extractItem(index - 1, 1, true).getCount() == ItemStack.EMPTY.getCount();  // -1 for Pizza Slot
    }

    protected ITextComponent getDefaultName()
    {
       return new TranslationTextComponent("container.granite_pizza_slab");
    }

    @Override
    protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_)
    {
        return null;
    }

    @Override
    public void setChanged()
    {
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        super.setChanged();
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(this.worldPosition, 42, this.save(new CompoundNBT()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        super.onDataPacket(net, pkt);
        this.load(this.getBlockState(), pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.save(super.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(BlockState blockState, CompoundNBT parentNBTTagCompound)
    {
        super.handleUpdateTag(blockState, parentNBTTagCompound);
        this.load(blockState, parentNBTTagCompound);
    }
}