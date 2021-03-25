package com.queekus.pizzachef.data.loot;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.items.ModItems;
import com.queekus.pizzachef.loot_modifiers.AdditionalItemsLootModifier;

import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModLootModifierProvider extends GlobalLootModifierProvider
{
    public ModLootModifierProvider(DataGenerator gen)
    {
        super(gen, PizzaChef.MOD_ID);
    }

    @Override
    protected void start()
    {
        add(
            "grass",
            new AdditionalItemsLootModifier.Serializer().setRegistryName("pizzachef:grass"),
            new AdditionalItemsLootModifier(
                new ILootCondition[]
                {
                    RandomChance.randomChance(0.125F).build(),
                    BlockStateProperty.hasBlockStateProperties(Blocks.GRASS).build()
                },
                new Item[]{ ModItems.tomato_seeds }));
        add(
            "sand",
            new AdditionalItemsLootModifier.Serializer().setRegistryName("pizzachef:sand"),
            new AdditionalItemsLootModifier(
                new ILootCondition[]
                {
                    RandomChance.randomChance(0.125F).build(),
                    BlockStateProperty.hasBlockStateProperties(Blocks.SAND).build()
                },
                new Item[]{ ModItems.salt }));
    }
}
