package com.queekus.pizzachef.data.recipes;

import java.util.function.Consumer;

import com.queekus.pizzachef.data.tags.ModTags;
import com.queekus.pizzachef.items.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;

public class ModRecipeProvider extends RecipeProvider
{
    public ModRecipeProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    // Can be used for both Shapeless AND Shaped Recipes
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer)
    {
        ShapelessRecipeBuilder
            .shapeless(ModItems.tomato_seeds)
            .requires(ModTags.Items.TOMATO)
            .unlockedBy("has_item", has(ModTags.Items.TOMATO))
            .save(consumer);

        ShapelessRecipeBuilder
            .shapeless(ModItems.granite_grinding_stone)
            .requires(Items.GRANITE)
            .requires(Items.SAND)
            .requires(Items.STICK)
            .requires(Items.PAPER)
            .unlockedBy("has_item", has(Items.GRANITE))
            .save(consumer);

        ShapelessRecipeBuilder
            .shapeless(ModItems.flour)
            .requires(ModItems.granite_grinding_stone)
            .requires(Items.WHEAT)
            .unlockedBy("has_item", has(ModItems.granite_grinding_stone))
            .save(consumer);

        ShapelessRecipeBuilder
            .shapeless(ModItems.tomato_puree)
            .requires(ModItems.granite_grinding_stone)
            .requires(ModItems.tomato)
            .unlockedBy("has_item", has(ModItems.granite_grinding_stone))
            .save(consumer);
}
}
