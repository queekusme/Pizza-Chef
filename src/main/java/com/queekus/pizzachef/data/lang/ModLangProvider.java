package com.queekus.pizzachef.data.lang;

import java.util.HashMap;
import java.util.Map;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

@SuppressWarnings({"serial", "unused"})
public class ModLangProvider extends LanguageProvider
{
    private String locale;

    private static final Map<String, Map<String, String>> LOCALE_DATA = new HashMap<String, Map<String, String>>()
    {{
        put("itemGroup.pizzachef", new HashMap<String, String>()
        {{
            put("en_us", "Pizza Chef");
        }});
        put(getRegistryIdFor(ModItems.pizza), new HashMap<String, String>()
        {{
            put("en_us", "Cooked Pizza");
        }});
        put(getRegistryIdFor(ModItems.pizza_slice), new HashMap<String, String>()
        {{
            put("en_us", "Cooked Pizza Slice");
        }});
        put(getRegistryIdFor(ModItems.pizza_base), new HashMap<String, String>()
        {{
            put("en_us", "Pizza Base");
        }});
        put(getRegistryIdFor(ModItems.tomato), new HashMap<String, String>()
        {{
            put("en_us", "Tomato");
        }});
        put(getRegistryIdFor(ModItems.tomato_seeds), new HashMap<String, String>()
        {{
            put("en_us", "Tomato Seeds");
        }});
        put(getRegistryIdFor(ModItems.tomato_puree), new HashMap<String, String>()
        {{
            put("en_us", "Tomato Purée");
        }});
        put(getRegistryIdFor(ModItems.block_of_cheese), new HashMap<String, String>()
        {{
            put("en_us", "Block of Cheese");
        }});
        put(getRegistryIdFor(ModItems.grated_cheese), new HashMap<String, String>()
        {{
            put("en_us", "Grated Cheese");
        }});
        put(getRegistryIdFor(ModItems.flour), new HashMap<String, String>()
        {{
            put("en_us", "Flour");
        }});
        put(getRegistryIdFor(ModItems.granite_grinding_stone), new HashMap<String, String>()
        {{
            put("en_us", "Granite Grinding Stone");
        }});
    }};

    public ModLangProvider(DataGenerator gen, String locale)
    {
        super(gen, PizzaChef.MOD_ID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations()
    {
        addMisc();
        addBlocks();
        addItems();
    }

    private void addMisc()
    {
        addMod("itemGroup.pizzachef");
    }

    private void addItems()
    {
        addMod(ModItems.pizza);
        addMod(ModItems.pizza_slice);
        addMod(ModItems.pizza_base);

        addMod(ModItems.tomato);
        addMod(ModItems.tomato_seeds);
        addMod(ModItems.tomato_puree);

        addMod(ModItems.block_of_cheese);
        addMod(ModItems.grated_cheese);

        addMod(ModItems.flour);

        addMod(ModItems.granite_grinding_stone);
    }

    private void addBlocks()
    {
        // addMod(ModBlocks.template_block);
    }

    private void addMod(String name) { add(name, getLocaleEntryFor(name, this.locale)); }
    private void addMod(Item item) { add(item, getLocaleEntryFor(item, this.locale)); }
    private void addMod(Block block) { add(block, getLocaleEntryFor(block, this.locale)); }

    private static String getRegistryIdFor(Item item) { return item.getRegistryName().getPath(); }
    private static String getRegistryIdFor(Block block) { return block.getRegistryName().getPath(); }

    private static String getLocaleEntryFor(Item item, String locale) { return getLocaleEntryFor(getRegistryIdFor(item), locale); }
    private static String getLocaleEntryFor(Block block, String locale) { return getLocaleEntryFor(getRegistryIdFor(block), locale); }
    private static String getLocaleEntryFor(String name, String locale) { return LOCALE_DATA.get(name).get(locale); }
}
