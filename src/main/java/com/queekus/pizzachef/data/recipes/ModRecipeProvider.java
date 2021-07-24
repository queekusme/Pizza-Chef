package com.queekus.pizzachef.data.recipes;

import java.util.function.Consumer;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.blocks.ModBlocks;
import com.queekus.pizzachef.crafting.ModCrafting;
import com.queekus.pizzachef.data.tags.ModTags;
import com.queekus.pizzachef.items.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

public class ModRecipeProvider extends RecipeProvider
{
    public ModRecipeProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    // Can be used for both Shapeless AND Shaped Recipes
    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer)
    {
        collectGraniteRecipes(consumer);
        collectCheeseRecipes(consumer);
        collectTomatoRecipes(consumer);
        collectMiscRecipes(consumer);

        cookRecipes(consumer, ModCrafting.SMELTING_TRANSFER_NBT.get(), "furnace", 300, 0.05F);
        cookRecipes(consumer, ModCrafting.SMOKER_TRANSFER_NBT.get(), "smoking", 100, 0.35F);
        cookRecipes(consumer, ModCrafting.CAMPFIRE_COOKING_TRANSFER_NBT.get(), "campfire_cooking", 600, 0.35F);
    }

    private void collectGraniteRecipes(Consumer<FinishedRecipe> consumer)
    {
        ShapedRecipeBuilder
            .shaped(ModBlocks.granite_pizza_slab, 8)
            .define('G', Items.GRANITE_SLAB)
            .pattern("GG ")
            .pattern("GG ")
            .pattern("  G")
            .unlockedBy("has_item", has(Items.GRANITE_SLAB))
            .save(consumer);

        ShapelessRecipeBuilder
            .shapeless(ModItems.granite_grinding_stone)
            .requires(Items.GRANITE)
            .requires(Items.SAND)
            .requires(Items.PAPER)
            .unlockedBy("has_item", has(Items.GRANITE))
            .save(consumer);
    }

    private void collectMiscRecipes(Consumer<FinishedRecipe> consumer)
    {
        ShapelessRecipeBuilder
            .shapeless(ModItems.flour)
            .requires(ModItems.granite_grinding_stone)
            .requires(Items.WHEAT)
            .unlockedBy("has_item", has(ModItems.granite_grinding_stone))
            .save(consumer);

        ShapelessRecipeBuilder
            .shapeless(ModItems.pizza_base)
            .requires(Ingredient.of(ModTags.Items.FLOUR_FORGE), 7)
            .requires(Items.WATER_BUCKET)
            .requires(ModItems.granite_grinding_stone)
            .unlockedBy("has_item", has(ModItems.flour))
            .save(consumer); // TODO: Recipe Book doesn't ignore damage, recipe works manually however

        ShapedRecipeBuilder
            .shaped(ModItems.pizza_cutter)
            .define('I', ModTags.Items.NUGGETS)
            .define('S', Items.STICK)
            .pattern("I ")
            .pattern(" S")
            .unlockedBy("has_item", has(ModTags.Items.NUGGETS))
            .save(consumer);
    }

    private void collectTomatoRecipes(Consumer<FinishedRecipe> consumer)
    {
        ShapelessRecipeBuilder
            .shapeless(ModItems.tomato_puree)
            .requires(ModItems.granite_grinding_stone)
            .requires(ModTags.Items.TOMATO)
            .unlockedBy("has_item", has(ModTags.Items.TOMATO))
            .save(consumer);

        ShapelessRecipeBuilder
            .shapeless(ModItems.tomato_seeds)
            .requires(ModTags.Items.TOMATO)
            .unlockedBy("has_item", has(ModTags.Items.TOMATO))
            .save(consumer);
    }

    private void collectCheeseRecipes(Consumer<FinishedRecipe> consumer)
    {
        ShapelessRecipeBuilder
            .shapeless(ModItems.block_of_cheese)
            .requires(ModTags.Items.SALT_FORGE)
            .requires(Items.MILK_BUCKET)
            .requires(Items.BOWL)
            .unlockedBy("has_item_milk", has(Items.MILK_BUCKET))
            .unlockedBy("has_item_bowl", has(Items.BOWL))
            .unlockedBy("has_item_salt", has(ModItems.salt))
            .save(consumer);

        ShapelessRecipeBuilder
            .shapeless(ModItems.grated_cheese, 8)
            .requires(ModTags.Items.CHEESE_FORGE)
            .requires(Items.BOWL)
            .requires(Items.STICK)
            .unlockedBy("has_item", has(ModItems.block_of_cheese))
            .save(consumer); // TODO: Recipe Book doesn't ignore damage, recipe works manually however
    }

    private void cookRecipes(Consumer<FinishedRecipe> consumer, SimpleCookingSerializer<?> serializer, String serializerId, int cookTime, float experience)
    {
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(ModItems.pizza_base), ModItems.pizza, experience, cookTime, serializer)
            .unlockedBy("has_pizza_base", has(ModItems.pizza_base))
            .save(consumer, new ResourceLocation(PizzaChef.MOD_ID, "cooked_pizza_from_" + serializerId));
    }
}
