package com.queekus.pizzachef.data.models;

import com.queekus.pizzachef.PizzaChef;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider
{
    private final ModelFile ITEM_GENERATED = getExistingFile(mcLoc("item/generated"));

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, PizzaChef.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        builder(ITEM_GENERATED, "pizza");
        builder(ITEM_GENERATED, "pizza_slice");
        builder(ITEM_GENERATED, "pizza_base");

        builder(ITEM_GENERATED, "tomato");
        builder(ITEM_GENERATED, "tomato_seeds");
        builder(ITEM_GENERATED, "tomato_puree");

        builder(ITEM_GENERATED, "block_of_cheese");
        builder(ITEM_GENERATED, "grated_cheese");

        builder(ITEM_GENERATED, "flour");

        builder(ITEM_GENERATED, "granite_grinding_stone");
    }

    private ItemModelBuilder builder(ModelFile file, String name)
    {
        return getBuilder(name).parent(file).texture("layer0", "item/" + name);
    }
}
