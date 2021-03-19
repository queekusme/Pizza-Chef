package com.queekus.pizzachef.data.models;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.blocks.ModBlocks;

import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
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
        ResourceLocation mcGraniteTexture = blockTexture(Blocks.POLISHED_GRANITE);
        simpleBlock(ModBlocks.granite_pizza_slab, models().slab(localGranitePizzaSlabId, mcGraniteTexture, mcGraniteTexture, mcGraniteTexture));
        simpleBlockItem(ModBlocks.granite_pizza_slab, models().getExistingFile(modLoc("block/" + localGranitePizzaSlabId)));
    }

    private void growingCrop(CropsBlock block, String name)
    {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        for(int i = 0; i <= 7; i++)
        {
            builder.partialState().with(CropsBlock.AGE, i)
                .modelForState().modelFile(models().crop("crop_tomato_" + i, modLoc("block/crop_tomato_" + i))).addModel();
        }
    }
}
