package com.queekus.pizzachef.blocks;

import com.queekus.pizzachef.tileentities.ModTileEntities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class GranitePizzaSlabBlock extends Block
{
    private static final VoxelShape BOUNDING = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

    public GranitePizzaSlabBlock(Properties props)
    {
        super(props);
    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_)
    {
       return BOUNDING;
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
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        TileEntity te = worldIn.getBlockEntity(pos);
        if (te instanceof IInventory)
            InventoryHelper.dropContents(worldIn, pos, (IInventory)te);

        super.onRemove(state, worldIn, pos, newState, isMoving);
    }
}
