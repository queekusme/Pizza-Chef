package com.queekus.pizzachef.data.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.ValidationTracker;
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
            // registerDropSelfLootTable(ModBlocks.template_block);
        }

        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            return new ArrayList<Block>(){{
                //add(ModBlocks.template_block);
            }};
        }
    }
}
