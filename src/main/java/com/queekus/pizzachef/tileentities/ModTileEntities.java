package com.queekus.pizzachef.tileentities;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.blocks.ModBlocks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities
{
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, PizzaChef.MOD_ID);

    public static final RegistryObject<BlockEntityType<TileEntityGranitePizzaSlab>> GRANITE_PIZZA_SLAB
        = BLOCK_ENTITIES.register("granite_pizza_slab", () -> BlockEntityType.Builder.of(TileEntityGranitePizzaSlab::new, ModBlocks.granite_pizza_slab).build(null));

    public static void register()
    {
        BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
