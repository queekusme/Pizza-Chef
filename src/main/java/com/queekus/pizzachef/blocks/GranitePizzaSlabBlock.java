package com.queekus.pizzachef.blocks;

import com.queekus.pizzachef.data.tags.ModTags;
import com.queekus.pizzachef.tileentities.ModTileEntities;
import com.queekus.pizzachef.tileentities.TileEntityGranitePizzaSlab;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

public class GranitePizzaSlabBlock extends BaseEntityBlock
{
    private static final VoxelShape BOUNDING = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

    public GranitePizzaSlabBlock(Properties props)
    {
        super(props);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(
        BlockState state,
        Level world,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hit
    )
    {
        if(ModTags.Items.PIZZA_CUTTER.contains(player.getItemInHand(hand).getItem()))
        {
            BlockEntity tile = world.getBlockEntity(pos);
            if(tile instanceof TileEntityGranitePizzaSlab)
            {
                ((TileEntityGranitePizzaSlab)tile).dropPizzaSlicesAsItems();
                return InteractionResult.SUCCESS;
            }
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_)
    {
       return BOUNDING;
    }

    // Mentions on Discord that this may be returning...
    //
    // @Override
    // public boolean hasTileEntity(BlockState state)
    // {
    //     return true;
    // }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return ModTileEntities.GRANITE_PIZZA_SLAB.get().create(blockPos, blockState);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        BlockEntity te = worldIn.getBlockEntity(pos);
        if (te instanceof Container)
        Containers.dropContents(worldIn, pos, (Container)te);

        super.onRemove(state, worldIn, pos, newState, isMoving);
    }
}
