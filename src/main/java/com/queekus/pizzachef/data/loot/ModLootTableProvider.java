package com.queekus.pizzachef.data.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.queekus.pizzachef.blocks.ModBlocks;
import com.queekus.pizzachef.items.ModItems;

import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.util.ResourceLocation;

public class ModLootTableProvider extends LootTableProvider
{
    public ModLootTableProvider(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
    }

    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        return ImmutableList.of(
            Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK)
        );
    }

    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((id, lootTable) -> LootTableManager.validate(validationtracker, id, lootTable));
    }

    @SuppressWarnings("serial")
    public static class ModBlockLootTables extends BlockLootTables
    {
        @Override
        protected void addTables()
        {
            add(
                ModBlocks.crop_tomato,
                createProduceCropDrops(
                    ModItems.tomato,
                    ModItems.tomato_seeds,
                    BlockStateProperty
                        .hasBlockStateProperties(ModBlocks.crop_tomato)
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                            .hasProperty(BeetrootBlock.AGE, 7))
                )
            );
        }

        protected static LootTable.Builder createProduceCropDrops(Item crop, Item seed, ILootCondition.IBuilder p_218541_3_) {
            return LootTable.lootTable()
                .withPool(
                    LootPool.lootPool()
                        .add(
                            ItemLootEntry.lootTableItem(crop)
                                .when(p_218541_3_)
                                .otherwise(ItemLootEntry.lootTableItem(seed))))
                .withPool(
                    LootPool.lootPool()
                        .when(p_218541_3_)
                        .add(
                            ItemLootEntry.lootTableItem(crop)
                                .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))));
        }

        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            return new ArrayList<Block>(){{
                add(ModBlocks.crop_tomato);
            }};
        }
    }
}
