package com.queekus.pizzachef.data.tags;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.items.ModItems;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagProvider extends ItemTagsProvider
{
    public ModItemTagProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper)
    {
        super(dataGenerator, blockTagProvider, PizzaChef.MOD_ID, existingFileHelper);
    }

    protected void addTags()
    {
        collectTomatoTypes();
        mergeTomatoTypes();

        collectPizzaIngredients();
    }

    private void collectTomatoTypes()
    {
        tag(ModTags.Items.TOMATO_FORGE_VEG).add(ModItems.tomato);
        tag(ModTags.Items.TOMATO_FORGE_FRUIT).add(ModItems.tomato);
        tag(ModTags.Items.TOMATO_FORGE_CROP).add(ModItems.tomato);
    }

    @SuppressWarnings("unchecked")
    private void mergeTomatoTypes()
    {
        tag(ModTags.Items.TOMATO).addTags(ModTags.Items.TOMATO_FORGE_VEG, ModTags.Items.TOMATO_FORGE_FRUIT, ModTags.Items.TOMATO_FORGE_CROP);
    }

    private void collectPizzaIngredients()
    {
        tag(ModTags.Items.PIZZA_INGREDIENTS).add(ModItems.grated_cheese);
        tag(ModTags.Items.PIZZA_INGREDIENTS).add(ModItems.tomato_puree);
        tag(ModTags.Items.PIZZA_INGREDIENTS).add(Items.COOKED_BEEF);
        tag(ModTags.Items.PIZZA_INGREDIENTS).add(Items.COOKED_PORKCHOP);
        tag(ModTags.Items.PIZZA_INGREDIENTS).add(Items.COOKED_CHICKEN);
        tag(ModTags.Items.PIZZA_INGREDIENTS).add(Items.COOKED_COD);
        tag(ModTags.Items.PIZZA_INGREDIENTS).add(Items.COOKED_MUTTON);
        tag(ModTags.Items.PIZZA_INGREDIENTS).add(Items.COOKED_RABBIT);
        tag(ModTags.Items.PIZZA_INGREDIENTS).add(Items.COOKED_SALMON);
        tag(ModTags.Items.PIZZA_INGREDIENTS).add(Items.BEETROOT);
        tag(ModTags.Items.PIZZA_INGREDIENTS).add(Items.MELON_SLICE);
    }
}
