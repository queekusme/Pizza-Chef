package com.queekus.pizzachef.blocks;

import com.queekus.pizzachef.tileentities.ModTileEntities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class GranitePizzaSlabBlock extends Block
{
    public GranitePizzaSlabBlock(Properties props)
    {
        super(props);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return ModTileEntities.GRANITE_PIZZA_SLAB.get().create();
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        TileEntity te = worldIn.getBlockEntity(pos);
        if (te instanceof IInventory)
            InventoryHelper.dropContents(worldIn, pos, (IInventory)te);

        super.onRemove(state, worldIn, pos, newState, isMoving);
    }
}
