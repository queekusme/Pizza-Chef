package com.queekus.pizzachef.data.tags;

import com.queekus.pizzachef.PizzaChef;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagProvider extends BlockTagsProvider
{
    public ModBlockTagProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, PizzaChef.MOD_ID, existingFileHelper);
    }

    protected void addTags()
    {

    }
}
