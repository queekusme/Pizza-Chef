package com.queekus.pizzachef.blocks;

import com.google.common.base.Function;
import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.items.ModItems;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(PizzaChef.MOD_ID)
public class ModBlocks
{
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PizzaChef.MOD_ID);

    public static final CropsBlock crop_tomato = null;
    public static final Block granite_pizza_slab = null;

    /**
     * Register a block under the Bottling namespace
     *
     * @param id - id of block to creater
     * @param constr - class constructor e.g. Block::new or CustomBlock::new
     * @param bProperties - block properties the block should have
     * @param createItem - whether an Item should be created for this block
     */
    public static void registerBlock(String id, Function<Properties, Block> constr, Properties bProperties, boolean createItem)
    {
        RegistryObject<Block> block = BLOCKS.register(id, () -> constr.apply(bProperties));

        if(createItem)
            ModItems.registerItemForBlock(id, BlockItem::new, block::get);
    }

    public static void register()
    {
        ModBlocks.registerBlock("crop_tomato", CropsBlock::new, Block.Properties.of(Material.PLANT).harvestTool(ToolType.HOE).randomTicks().noCollission().sound(SoundType.HARD_CROP), false);
        ModBlocks.registerBlock("granite_pizza_slab", GranitePizzaSlabBlock::new, Block.Properties.of(Material.STONE, MaterialColor.DIRT).requiresCorrectToolForDrops().strength(1.5F, 6.0F).noOcclusion(), true);

        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
