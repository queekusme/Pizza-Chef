package com.queekus.pizzachef.data.tags;

import com.queekus.pizzachef.PizzaChef;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags
{
    public static final class Blocks
    {

    }

    public static final class Items
    {
        public static final ITag.INamedTag<Item> PIZZA_INGREDIENTS = mod("pizza_toppings"); // Ingredients which can be put on a pizza base

        public static final ITag.INamedTag<Item> TOMATO = forge("tomato");
        public static final ITag.INamedTag<Item> TOMATO_FORGE_VEG = forge("items/vegetables/tomato");
        public static final ITag.INamedTag<Item> TOMATO_FORGE_FRUIT = forge("items/fruits/tomato");
        public static final ITag.INamedTag<Item> TOMATO_FORGE_CROP = forge("items/crops/tomato");

        private static ITag.INamedTag<Item> forge(String path)
        {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path)
        {
            return ItemTags.bind(new ResourceLocation(PizzaChef.MOD_ID, path).toString());
        }
    }
}
