package com.queekus.pizzachef;

import com.queekus.pizzachef.blocks.ModBlocks;
import com.queekus.pizzachef.crafting.ModCrafting;
import com.queekus.pizzachef.items.ModItems;
import com.queekus.pizzachef.loot_modifiers.ModLootModifiers;
import com.queekus.pizzachef.tileentities.ModTileEntities;
import com.queekus.pizzachef.tileentities.renderers.GranitePizzaSlabTileEntityRenderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

@Mod(PizzaChef.MOD_ID)
public class PizzaChef
{
    public static final String MOD_ID = "pizzachef";

    public static CreativeModeTab creativeTab = new PizzaChefItemGroup(PizzaChef.MOD_ID, () -> ModItems.pizza);

    public PizzaChef()
    {
        ModBlocks.register();
        ModItems.register();
        ModCrafting.register();
        ModTileEntities.register();
        ModLootModifiers.register();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private static class PizzaChefItemGroup extends CreativeModeTab
    {
        private ItemLike icon;

        public PizzaChefItemGroup(String label, ItemLike icon) { super(label); this.icon = icon; }
        @Override public ItemStack makeIcon() { return new ItemStack(icon); }
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.crop_tomato, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.granite_pizza_slab, RenderType.cutout());

        ClientRegistry.bindTileEntityRenderer(ModTileEntities.GRANITE_PIZZA_SLAB.get(), GranitePizzaSlabTileEntityRenderer::new);
    }
}
