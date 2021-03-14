package com.queekus.pizzachef;

import com.queekus.pizzachef.items.ModItems;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.common.Mod;

@Mod(PizzaChef.MOD_ID)
public class PizzaChef
{
    public static final String MOD_ID = "pizzachef";

    public static ItemGroup creativeTab = new PizzaChefItemGroup(PizzaChef.MOD_ID, () -> ModItems.pizza);

    public PizzaChef()
    {
        ModItems.register();
    }

    private static class PizzaChefItemGroup extends ItemGroup
    {
        private IItemProvider icon;

        public PizzaChefItemGroup(String label, IItemProvider icon) { super(label); this.icon = icon; }
        @Override public ItemStack makeIcon() { return new ItemStack(icon); }
    }
}
