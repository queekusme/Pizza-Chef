package com.queekus.pizzachef.blocks;

import com.queekus.pizzachef.data.tags.ModTags;
import com.queekus.pizzachef.tileentities.ModTileEntities;
import com.queekus.pizzachef.tileentities.TileEntityGranitePizzaSlab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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

    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType use(
        BlockState state,
        World world,
        BlockPos pos,
        PlayerEntity player,
        Hand hand,
        BlockRayTraceResult hit
    )
    {
        if(ModTags.Items.PIZZA_CUTTER.contains(player.getItemInHand(hand).getItem()))
        {
            TileEntity tile = world.getBlockEntity(pos);
            if(tile instanceof TileEntityGranitePizzaSlab)
            {
                ((TileEntityGranitePizzaSlab)tile).dropPizzaSlicesAsItems();
                return ActionResultType.SUCCESS;
            }
        }

        return super.use(state, world, pos, player, hand, hit);
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
