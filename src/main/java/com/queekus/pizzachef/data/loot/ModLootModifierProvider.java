package com.queekus.pizzachef.data.loot;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.items.ModItems;
import com.queekus.pizzachef.loot_modifiers.AdditionalItemsLootModifier;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;

public class ModLootModifierProvider extends GlobalLootModifierProvider
{
    public ModLootModifierProvider(DataGenerator gen)
    {
        super(gen, PizzaChef.MOD_ID);
    }

    @Override
    protected void start()
    {
        GlobalLootModifierSerializer<AdditionalItemsLootModifier> serializerInstance
            = new AdditionalItemsLootModifier.Serializer().setRegistryName("pizzachef:additional");

        add(
            "grass",
            serializerInstance,
            new AdditionalItemsLootModifier(
                new LootItemCondition[]
                {
                    LootItemRandomChanceCondition.randomChance(0.125F).build(),
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build()
                },
                new Item[]{ ModItems.tomato_seeds }));
        add(
            "sand",
            serializerInstance,
            new AdditionalItemsLootModifier(
                new LootItemCondition[]
                {
                    LootItemRandomChanceCondition.randomChance(0.125F).build(),
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SAND).build()
                },
                new Item[]{ ModItems.salt }));
    }
}
