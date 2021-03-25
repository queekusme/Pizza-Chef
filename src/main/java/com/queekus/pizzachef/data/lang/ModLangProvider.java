package com.queekus.pizzachef.data.lang;

import java.util.ArrayList;
import java.util.List;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.blocks.ModBlocks;
import com.queekus.pizzachef.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

@SuppressWarnings({"serial"})
public class ModLangProvider extends LanguageProvider
{
    private String locale;

    private List<ModLocaleEntry<String>> miscEntries = new ArrayList<ModLocaleEntry<String>>()
    {{
        add(ModLocaleEntry.create("itemGroup.pizzachef", (entry) ->
        {
            entry.addEntry("en_us", "Pizza Chef");
        }));
        add(ModLocaleEntry.create("pizza.lore.left", (entry) ->
        {
            entry.addEntry("en_us", "Left");
        }));
        add(ModLocaleEntry.create("pizza.lore.right", (entry) ->
        {
            entry.addEntry("en_us", "Right");
        }));
        add(ModLocaleEntry.create("container.granite_pizza_slab", (entry) ->
        {
            entry.addEntry("en_us", "Granite Pizza Slab");
        }));
    }};

    private List<ModLocaleEntry<Item>> itemEntries = new ArrayList<ModLocaleEntry<Item>>()
    {{
        add(ModLocaleEntry.create(ModItems.pizza, (entry) ->
        {
            entry.addEntry("en_us", "Cooked Pizza");
        }));
        add(ModLocaleEntry.create(ModItems.pizza_slice, (entry) ->
        {
            entry.addEntry("en_us", "Cooked Pizza Slice");
        }));
        add(ModLocaleEntry.create(ModItems.pizza_base, (entry) ->
        {
            entry.addEntry("en_us", "Pizza Base");
        }));

        add(ModLocaleEntry.create(ModItems.tomato, (entry) ->
        {
            entry.addEntry("en_us", "Tomato");
        }));
        add(ModLocaleEntry.create(ModItems.tomato_seeds, (entry) ->
        {
            entry.addEntry("en_us", "Tomato Seeds");
        }));
        add(ModLocaleEntry.create(ModItems.tomato_puree, (entry) ->
        {
            entry.addEntry("en_us", "Tomato Purée");
        }));

        add(ModLocaleEntry.create(ModItems.salt, (entry) ->
        {
            entry.addEntry("en_us", "Salt");
        }));

        add(ModLocaleEntry.create(ModItems.block_of_cheese, (entry) ->
        {
            entry.addEntry("en_us", "Block of Cheese");
        }));
        add(ModLocaleEntry.create(ModItems.grated_cheese, (entry) ->
        {
            entry.addEntry("en_us", "Grated Cheese");
        }));

        add(ModLocaleEntry.create(ModItems.flour, (entry) ->
        {
            entry.addEntry("en_us", "Flour");
        }));
        add(ModLocaleEntry.create(ModItems.granite_grinding_stone, (entry) ->
        {
            entry.addEntry("en_us", "Granite Grinding Stone");
        }));

        add(ModLocaleEntry.create(ModItems.pizza_cutter, (entry) ->
        {
            entry.addEntry("en_us", "Pizza Cutter");
        }));
    }};

    private List<ModLocaleEntry<Block>> blockEntries = new ArrayList<ModLocaleEntry<Block>>()
    {{
        add(ModLocaleEntry.create(ModBlocks.granite_pizza_slab, (entry) ->
        {
            entry.addEntry("en_us", "Granite Pizza Slab");
        }));
    }};

    public ModLangProvider(DataGenerator gen, String locale)
    {
        super(gen, PizzaChef.MOD_ID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations()
    {
        this.miscEntries.forEach((entry) -> entry.register(this::add, this.locale));
        this.blockEntries.forEach((entry) -> entry.register(this::add, this.locale));
        this.itemEntries.forEach((entry) -> entry.register(this::add, this.locale));
    }
}
