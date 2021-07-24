package com.queekus.pizzachef.tileentities;

import java.util.Arrays;

import javax.annotation.Nullable;

import com.queekus.pizzachef.api.IPizza;
import com.queekus.pizzachef.api.PizzaInventoryHandler;
import com.queekus.pizzachef.api.PizzaSide;
import com.queekus.pizzachef.crafting.NBTCrafting;
import com.queekus.pizzachef.items.ModItems;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.Containers;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraft.core.BlockPos;

public class TileEntityGranitePizzaSlab extends BaseContainerBlockEntity implements WorldlyContainer
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

    public TileEntityGranitePizzaSlab(BlockPos blockPos, BlockState blockState)
    {
        super(ModTileEntities.GRANITE_PIZZA_SLAB.get(), blockPos, blockState);
    }

    public void dropPizzaSlicesAsItems()
    {
        if(this.getPizza().getItem() != ModItems.pizza)
            return;

        Containers.dropItemStack(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), this.getSlice(PizzaSide.LEFT, 4));
        Containers.dropItemStack(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), this.getSlice(PizzaSide.RIGHT, 4));

        ContainerHelper.takeItem(this.pizzStack, PIZZA_SLOT); // Ignore output, we just wanna remove it
    }

    private ItemStack getSlice(PizzaSide side, int count)
    {
        if(this.level.isClientSide || this.getPizza() == ItemStack.EMPTY)
            return ItemStack.EMPTY;

        ItemStack stack = new ItemStack(ModItems.pizza_slice, count);

        ItemStack pizzaCopy = this.getPizza().copy(); // Reduce as pizza to retain information about whole pizza item
        ((PizzaInventoryHandler)IPizza.getHandlerForPizza(pizzaCopy)).reduceToSide(side).defrag();

        NBTCrafting.transferNBT(stack, pizzaCopy);

        return stack;
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

    public static int[] getPizzaLeftSlots()
    {
        return new int[]{ LEFT_0, LEFT_1, LEFT_2, LEFT_3 };
    }

    public static int[] getPizzaRightSlots()
    {
        return new int[]{ RIGHT_0, RIGHT_1, RIGHT_2, RIGHT_3 };
    }

    @Override
    public int getContainerSize()
    {
        int internalPizzaSlots = this.hasPizza() ? this.getPizzaHandler().getSlots() : 0;

        return internalPizzaSlots + 1; // + 1 for pizza
    }

    @Override
    public void load(CompoundTag nbt)
    {
        super.load(nbt);

        this.pizzStack.clear(); // remove current and replace with new

        ContainerHelper.loadAllItems(nbt, this.pizzStack);
    }

    @Override
    public CompoundTag save(CompoundTag compound)
    {
        super.save(compound);

        return ContainerHelper.saveAllItems(compound, this.pizzStack);
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
                return this.getPizzaHandler().getStackInSlot(index - 1); // -1 for Pizza Slot
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
                itemStack = ContainerHelper.takeItem(this.pizzStack, index); // Ignore count as we only handle 1 per slot
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
                return ContainerHelper.takeItem(this.pizzStack, index);
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
    public boolean stillValid(Player player)
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

    protected Component getDefaultName()
    {
       return new TranslatableComponent("container.granite_pizza_slab");
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_)
    {
        return null;
    }

    @Override
    public void setChanged()
    {
        if(!level.isClientSide)
            this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        super.setChanged();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 42, this.save(new CompoundTag()));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        super.onDataPacket(net, pkt);
        this.load(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.save(super.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(CompoundTag parentNBTTagCompound)
    {
        super.handleUpdateTag(parentNBTTagCompound);
        this.load(parentNBTTagCompound);
    }
}