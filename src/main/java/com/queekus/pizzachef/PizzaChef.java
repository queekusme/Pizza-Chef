package com.queekus.pizzachef;

import com.queekus.pizzachef.blocks.ModBlocks;
import com.queekus.pizzachef.items.ModItems;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PizzaChef.MOD_ID)
public class PizzaChef
{
    public static final String MOD_ID = "pizzachef";

    public static ItemGroup creativeTab = new PizzaChefItemGroup(PizzaChef.MOD_ID, () -> ModItems.pizza);

    public PizzaChef()
    {
        ModBlocks.register();
        ModItems.register();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private static class PizzaChefItemGroup extends ItemGroup
    {
        private IItemProvider icon;

        public PizzaChefItemGroup(String label, IItemProvider icon) { super(label); this.icon = icon; }
        @Override public ItemStack makeIcon() { return new ItemStack(icon); }
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.crop_tomato, RenderType.cutout());
    }
}
