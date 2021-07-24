package com.queekus.pizzachef.items;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.blocks.ModBlocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(PizzaChef.MOD_ID)
public class ModItems
{
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PizzaChef.MOD_ID);

    public static final Item pizza = null;
    public static final Item pizza_slice = null;
    public static final Item pizza_base = null;

    public static final Item tomato = null;
    public static final Item tomato_seeds = null;
    public static final Item tomato_puree = null;

    public static final Item salt = null;

    public static final Item block_of_cheese = null;
    public static final Item grated_cheese = null;

    public static final Item flour = null;
    public static final Item granite_grinding_stone = null;

    public static final Item pizza_cutter = null;

    /**
     * Register an item under the pizzachef namespace
     *
     * @param id - id of item to create
     * @param constr - class constructor e.g. Item::new or CustomItem::new
     * @param bProperties - item properties the item should have
     */
    public static void registerItem(String id, Function<Properties, Item> constr, Properties iProperties)
    {
        ITEMS.register(id, () -> constr.apply(iProperties.tab(PizzaChef.creativeTab)));
    }

    /**
     * Register an item under the pizzachef namespace
     *
     * @param id - id of item to create
     * @param constr - class constructor e.g. Item::new or CustomItem::new
     * @param bProperties - item properties the item should have
     */
    public static void registerPizzaTopping(String id, BiFunction<Properties, Float, Item> constr, Properties iProperties, float tesrScale)
    {
        ITEMS.register(id, () -> constr.apply(iProperties.tab(PizzaChef.creativeTab), tesrScale));
    }

    /**
     * Register an item for a block under the pizzachef namespace
     *
     * @param id - id of item to create
     * @param constr - class constructor e.g. BlockItem::new or CustomBlockItem::new
     * @param block - block to register an item for
     */
    public static void registerItemForBlock(String id, BiFunction<Block, Properties, BlockItem> constr, Supplier<Block> block)
    {
        ITEMS.register(id, () -> constr.apply(block.get(), new Item.Properties().tab(PizzaChef.creativeTab)));
    }

    public static void register()
    {
        ModItems.registerItem("pizza", PizzaItem::new, new Item.Properties());
        ModItems.registerItem("pizza_slice", PizzaItem::new, new Item.Properties());
        ModItems.registerItem("pizza_base", PizzaItem::new, new Item.Properties());

        ModItems.registerItem("tomato", Item::new, new Item.Properties());
        ModItems.registerItemForBlock("tomato_seeds", BlockItem::new, () -> ModBlocks.crop_tomato);
        ModItems.registerPizzaTopping("tomato_puree", PizzaToppingItem::new, new Item.Properties(), 0.2f);

        ModItems.registerItem("salt", Item::new, new Item.Properties());

        ModItems.registerItem("block_of_cheese", Item::new, new Item.Properties());
        ModItems.registerPizzaTopping("grated_cheese", PizzaToppingItem::new, new Item.Properties(), 0.2f);

        ModItems.registerItem("flour", Item::new, new Item.Properties());

        ModItems.registerItem("granite_grinding_stone", CraftingRetainedItem::new, new Item.Properties().durability(64).setNoRepair());

        ModItems.registerItem("pizza_cutter", Item::new, new Item.Properties().durability(64));

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
