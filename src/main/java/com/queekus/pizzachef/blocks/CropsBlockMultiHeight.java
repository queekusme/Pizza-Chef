package com.queekus.pizzachef.blocks;

import java.util.Collections;
import java.util.Random;

import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.server.ServerWorld;

public class CropBlockMultiHeight extends CropBlock
{
    public static final IntegerProperty HEIGHT_2 = IntegerProperty.create("height", 0, 1); // 0, 1 = 2 high
    public static final IntegerProperty HEIGHT_3 = IntegerProperty.create("height", 0, 2); // 0, 1, 2 = 3 high

    private IntegerProperty heightProperty;

    private CropBlockMultiHeight(Properties props, IntegerProperty heightProperty)
    {
        super(props);
        this.heightProperty = heightProperty;
        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(this.getAgeProperty(), Integer.valueOf(0))
                .setValue(this.heightProperty, Integer.valueOf(0)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        super.onRemove(state, worldIn, pos, newState, isMoving);

        if(newState.getBlock() == this)
            return;

        worldIn.destroyBlock(pos.below(state.getValue(this.heightProperty)), isMoving);
    }

    public Integer getMaxPlantHeight()
    {
        return Collections.max(this.heightProperty.getPossibleValues());
    }

    public IntegerProperty getHeightProperty()
    {
       return this.heightProperty;
    }

    public boolean isMaxHeight(BlockState state)
    {
        return state.getValue(this.getHeightProperty()) >= this.getMaxPlantHeight();
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.below()).getBlock() == this || super.canSurvive(state, worldIn, pos);
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
    {
        if(!this.hasFurtherGrowthAvailable(state, worldIn, pos))
            return false;

        if(this.isMaxAge(state))
        {
            if(worldIn.getBlockState(pos.above()).getBlock() instanceof CropBlockMultiHeight)
                return this.isValidBonemealTarget(worldIn, pos.above(), worldIn.getBlockState(pos.above()), isClient);
            else if(worldIn.getBlockState(pos.above()).getBlock() instanceof AirBlock)
                return true;
        }

        return super.isValidBonemealTarget(worldIn, pos, state, isClient);
    }

    @Override
    public void growCrops(Level worldIn, BlockPos pos, BlockState state)
    {
        this.growAt(worldIn, state, pos, this.getBonemealAgeIncrease(worldIn));
    }

    public void growAt(Level worldIn, BlockState state, BlockPos pos, int by)
    {
        int localCurrent = this.getAge(state);
        int maxAge = this.getMaxAge();

        int localBy = localCurrent + by;

        if(localBy > maxAge)
        {
            int nextBy = localBy - maxAge;

            if(worldIn.getBlockState(pos.above()).getBlock() instanceof AirBlock)
            {
                boolean canCreateBlockAbove = worldIn.getBlockState(pos.above()).getBlock() instanceof AirBlock;
                boolean isMaxHeight = this.isMaxHeight(state);

                if(canCreateBlockAbove && !isMaxHeight)
                    worldIn.setBlock(pos.above(), this.getStateForAgeAndHeight(state, nextBy, state.getValue(this.heightProperty) + 1), 2);
            }
            else
                this.growAt(worldIn, worldIn.getBlockState(pos.above()), pos.above(), nextBy);

            localBy -= nextBy; // Decrease amount we have handled
        }

        worldIn.setBlock(pos, this.getStateForAgeAndHeight(state, localBy, state.getValue(this.heightProperty)), 2);
    }

    private boolean hasFurtherGrowthAvailable(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        boolean canGrowInCurrentPosition = this.getAge(state) < this.getMaxAge();
        if(pos.getY() + this.getMaxPlantHeight() >= worldIn.getMaxBuildHeight())
            return canGrowInCurrentPosition;

        if(this.isMaxHeight(state)) // At max height
            return canGrowInCurrentPosition;

        BlockState blockStateAbove = worldIn.getBlockState(pos.above());
        if(blockStateAbove.getBlock() instanceof AirBlock)
            return true;

        return blockStateAbove.getBlock() instanceof CropBlockMultiHeight && this.getAge(blockStateAbove) < this.getMaxAge();
    }

    public BlockState getStateForAgeAndHeight(BlockState state, int age, int height)
    {
       return super.getStateForAge(age).setValue(this.getHeightProperty(), height);
    }

    public boolean isRandomlyTicking(BlockState p_149653_1_)
    {
       return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        // === SUPER FOR RANDOM_TICK ===
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getRawBrightness(pos, 0) >= 9)
        {
            int i = this.getAge(state);
            if (i < this.getMaxAge())
            {
                float f = getGrowthSpeed(this, worldIn, pos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0))
                {
                    // Modified Below to make height aware
                    worldIn.setBlock(pos, this.getStateForAgeAndHeight(state, i + 1, state.getValue(this.getHeightProperty())), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }
        }
        // === END SUPER FOR RANDOM_TICK ===

        boolean isMaxAge = this.isMaxAge(state);
        boolean canCreateBlockAbove = worldIn.getBlockState(pos.above()).getBlock() instanceof AirBlock;
        boolean isMaxHeight = this.isMaxHeight(state);

        if(isMaxAge && canCreateBlockAbove && !isMaxHeight)
        {
            worldIn.setBlock(pos.above(), this.getStateForAgeAndHeight(state, 0, state.getValue(this.heightProperty) + 1), 2);
        }
    }

    public static class TwoTall extends CropBlockMultiHeight
    {
        public TwoTall(Properties props)
        {
            super(props, HEIGHT_2);
        }

        @Override
        protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
        {
            super.createBlockStateDefinition(builder);
            builder.add(HEIGHT_2);
        }
    }

    public static class ThreeTall extends CropBlockMultiHeight
    {
        public ThreeTall(Properties props)
        {
            super(props, HEIGHT_3);
        }

        @Override
        protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
        {
            super.createBlockStateDefinition(builder);
            builder.add(HEIGHT_3);
        }
    }
}
