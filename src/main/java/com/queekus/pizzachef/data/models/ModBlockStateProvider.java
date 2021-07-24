package com.queekus.pizzachef.data.models;

import java.util.Collections;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.blocks.CropBlockMultiHeight;
import com.queekus.pizzachef.blocks.ModBlocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider
{
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, PizzaChef.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        growingCrop(ModBlocks.crop_tomato, "crop_tomato");

        String localGranitePizzaSlabId = ModBlocks.granite_pizza_slab.getRegistryName().getPath();
        simpleBlock(ModBlocks.granite_pizza_slab, models().carpet(localGranitePizzaSlabId, blockTexture(Blocks.POLISHED_GRANITE)));
        simpleBlockItem(ModBlocks.granite_pizza_slab, models().getExistingFile(modLoc("block/" + localGranitePizzaSlabId)));
    }

    private void growingCrop(CropBlock block, String name)
    {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        for(int i = 0; i <= Collections.max(BlockStateProperties.AGE_7.getPossibleValues()); i++)
        {
            for(int j = 0; j <= Collections.max(CropBlockMultiHeight.HEIGHT_2.getPossibleValues()); j++)
            {
                String cropFileName = "crop_tomato_" + j + "_" + i;
                builder.partialState()
                    .with(CropBlock.AGE, i)
                    .with(CropBlockMultiHeight.HEIGHT_2, j)
                    .modelForState().modelFile(models().crop(cropFileName, modLoc("block/" + cropFileName))).addModel();
            }
        }
    }
}
