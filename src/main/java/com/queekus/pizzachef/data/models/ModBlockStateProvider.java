package com.queekus.pizzachef.data.models;

import com.queekus.pizzachef.PizzaChef;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
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
        // simpleBlock(ModBlocks.template_block);
        // simpleBlockItem(ModBlocks.template_block, models().getExistingFile(modLoc("block/template_block")));
    }
}
