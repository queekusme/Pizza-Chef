package com.queekus.pizzachef.blocks;

import com.queekus.pizzachef.items.ModItems;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ItemLike;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

public class CropTomato extends CropBlockMultiHeight.TwoTall
{
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
        Block.box(4.0D, 0.0D, 4.0D, 12.0D, 2.0D, 12.0D),
        Block.box(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D),
        Block.box(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),
        Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D),
        Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
        Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D),
        Block.box(4.0D, 0.0D, 4.0D, 12.0D, 14.0D, 12.0D),
        Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D),
    };

    public CropTomato(Properties props)
    {
        super(props);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
       return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    @Override
    protected ItemLike getBaseSeedId()
    {
        return ModItems.tomato_seeds;
    }
}
