package com.queekus.pizzachef.loot_modifiers;

import com.queekus.pizzachef.PizzaChef;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModLootModifiers
{
    private static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZERS
        = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, PizzaChef.MOD_ID);

    public static void register()
    {
        LOOT_MODIFIER_SERIALIZERS.register("additional", AdditionalItemsLootModifier.Serializer::new);

        LOOT_MODIFIER_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
