package com.queekus.pizzachef.data.recipes;

import java.util.function.Consumer;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.crafting.ModCrafting;
import com.queekus.pizzachef.data.tags.ModTags;
import com.queekus.pizzachef.items.ModItems;

import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

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
            .requires(ModTags.Items.TOMATO)
            .unlockedBy("has_item", has(ModTags.Items.TOMATO))
            .save(consumer);

        ShapelessRecipeBuilder
            .shapeless(ModItems.pizza_base)
            .requires(ModItems.flour, 7) // Review if forge tag exists for this
            .requires(Items.WATER_BUCKET)
            .requires(ModItems.granite_grinding_stone)
            .unlockedBy("has_item", has(ModItems.flour))
            .save(consumer); // TODO: Recipe Book doesn't ignore damage, recipe works manually however

        ShapelessRecipeBuilder
            .shapeless(ModItems.grated_cheese)
            .requires(ModItems.block_of_cheese) // Review if forge tag exists for this
            .unlockedBy("has_item", has(ModItems.block_of_cheese))
            .save(consumer); // TODO: Recipe Book doesn't ignore damage, recipe works manually however

        ShapelessRecipeBuilder
            .shapeless(ModItems.block_of_cheese)
            .requires(ModItems.grated_cheese, 8) // Review if forge tag exists for this
            .requires(ModItems.granite_grinding_stone)
            .unlockedBy("has_item", has(ModItems.grated_cheese))
            .save(consumer); // TODO: Recipe Book doesn't ignore damage, recipe works manually however

         // cookRecipes(consumer, ModCrafting.SMOKER_TRANSFER_NBT.get(), "smoking", 100);  // Currently non-operational due to AbstractFurnaceTileEntity::burn using IRecipe::getResultItem rather than IRecipe::assemble
        cookRecipes(consumer, ModCrafting.CAMPFIRE_COOKING_TRANSFER_NBT.get(), "campfire_cooking", 600);
    }

    private void cookRecipes(Consumer<IFinishedRecipe> consumer, CookingRecipeSerializer<?> serializer, String serializerId, int cookTime)
    {
        CookingRecipeBuilder.cooking(Ingredient.of(ModItems.pizza_base), ModItems.pizza, 0.35F, cookTime, serializer)
            .unlockedBy("has_pizza_base", has(ModItems.pizza_base))
            .save(consumer, new ResourceLocation(PizzaChef.MOD_ID, "cooked_pizza_from_" + serializerId));
    }
}
