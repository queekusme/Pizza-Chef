package com.queekus.pizzachef.items;

import java.util.function.Function;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.utils.Function2;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
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

    public static final Item block_of_cheese = null;
    public static final Item grated_cheese = null;

    public static final Item flour = null;

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
     * Register an item for a block under the pizzachef namespace
     *
     * @param id - id of item to create
     * @param constr - class constructor e.g. BlockItem::new or CustomBlockItem::new
     * @param block - block to register an item for
     */
    public static void registerItemForBlock(String id, Function2<Block, Properties, BlockItem> constr, RegistryObject<Block> block)
    {
        ITEMS.register(id, () -> constr.apply(block.get(), new Item.Properties().tab(PizzaChef.creativeTab)));
    }

    public static void register()
    {
        ModItems.registerItem("pizza", Item::new, new Item.Properties());
        ModItems.registerItem("pizza_slice", Item::new, new Item.Properties());
        ModItems.registerItem("pizza_base", Item::new, new Item.Properties());

        ModItems.registerItem("tomato", Item::new, new Item.Properties());
        ModItems.registerItem("tomato_seeds", Item::new, new Item.Properties());
        ModItems.registerItem("tomato_puree", Item::new, new Item.Properties());

        ModItems.registerItem("block_of_cheese", Item::new, new Item.Properties());
        ModItems.registerItem("grated_cheese", Item::new, new Item.Properties());

        ModItems.registerItem("flour", Item::new, new Item.Properties());

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
