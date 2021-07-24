package com.queekus.pizzachef.data.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.queekus.pizzachef.blocks.CropBlockMultiHeight;
import com.queekus.pizzachef.blocks.ModBlocks;
import com.queekus.pizzachef.items.ModItems;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ModLootTableProvider extends LootTableProvider
{
    public ModLootTableProvider(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
    }

    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables()
    {
        return ImmutableList.of(
            Pair.of(ModBlockLootTables::new, LootContextParamSets.BLOCK)
        );
    }

    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationContext) {
        map.forEach((id, lootTable) -> LootTables.validate(validationContext, id, lootTable));
    }

    @SuppressWarnings("serial")
    public static class ModBlockLootTables extends BlockLoot
    {
        @Override
        protected void addTables()
        {
            add(
                ModBlocks.crop_tomato,
                createProduceCropDrops(
                    ModItems.tomato,
                    ModItems.tomato_seeds,
                    LootItemBlockStatePropertyCondition
                        .hasBlockStateProperties(ModBlocks.crop_tomato)
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                            .hasProperty(CropBlock.AGE, 7)
                            .hasProperty(CropBlockMultiHeight.HEIGHT_2, 1))
                )
            );
            add(ModBlocks.granite_pizza_slab, BlockLoot::createNameableBlockEntityTable);
        }

        protected static LootTable.Builder createProduceCropDrops(Item crop, Item seed, LootItemCondition.Builder cropCondition)
        {
            return LootTable.lootTable()
                .withPool(
                    LootPool.lootPool()
                        .add(
                            LootItem.lootTableItem(crop)
                                .when(cropCondition)
                                .otherwise(
                                    LootItem.lootTableItem(seed))))
                .withPool(
                    LootPool.lootPool()
                        .when(cropCondition)
                        .add(
                            LootItem.lootTableItem(crop)
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))));
        }

        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            return new ArrayList<Block>(){{
                add(ModBlocks.crop_tomato);
                add(ModBlocks.granite_pizza_slab);
            }};
        }
    }
}
