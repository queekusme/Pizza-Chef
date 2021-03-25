package com.queekus.pizzachef.blocks;

import com.queekus.pizzachef.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CropTomato extends CropsBlockMultiHeight.TwoTall
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

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
       return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    @Override
    protected IItemProvider getBaseSeedId()
    {
        return ModItems.tomato_seeds;
    }
}
