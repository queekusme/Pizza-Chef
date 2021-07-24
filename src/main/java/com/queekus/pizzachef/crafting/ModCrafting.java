package com.queekus.pizzachef.crafting;

import com.queekus.pizzachef.PizzaChef;

import net.minecraft.world.item.crafting.CookingRecipeSerializer;
import net.minecraft.world.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModCrafting
{
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PizzaChef.MOD_ID);

    public static final RegistryObject<CookingRecipeSerializer<?>> SMOKER_TRANSFER_NBT
        = RECIPE_SERIALIZERS.register("smoker_transfer_nbt", () -> new CookingRecipeSerializer<>(TransferNBTSmokerRecipe::new, 100));
    public static final RegistryObject<CookingRecipeSerializer<?>> CAMPFIRE_COOKING_TRANSFER_NBT
        = RECIPE_SERIALIZERS.register("campfire_cooking_transfer_nbt", () -> new CookingRecipeSerializer<>(TransferNBTCampfireRecipe::new, 100));
    public static final RegistryObject<CookingRecipeSerializer<?>> BLASTING_TRANSFER_NBT
        = RECIPE_SERIALIZERS.register("blasting_transfer_nbt", () -> new CookingRecipeSerializer<>(TransferNBTBlasterRecipe::new, 100));
    public static final RegistryObject<CookingRecipeSerializer<?>> SMELTING_TRANSFER_NBT
        = RECIPE_SERIALIZERS.register("smelting_transfer_nbt", () -> new CookingRecipeSerializer<>(TransferNBTSmeltingRecipe::new, 200));

    public static void register()
    {
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
