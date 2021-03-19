package com.queekus.pizzachef.tileentities;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.blocks.ModBlocks;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities
{
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, PizzaChef.MOD_ID);

    public static final RegistryObject<TileEntityType<TileEntityGranitePizzaSlab>> GRANITE_PIZZA_SLAB
        = TILE_ENTITIES.register("granite_pizza_slab", () -> TileEntityType.Builder.of(TileEntityGranitePizzaSlab::new, ModBlocks.granite_pizza_slab).build(null));

    public static void register()
    {
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
