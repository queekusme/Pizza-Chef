package com.queekus.pizzachef.data;

import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.data.lang.ModLangProvider;
import com.queekus.pizzachef.data.loot.ModLootTableProvider;
import com.queekus.pizzachef.data.models.ModBlockStateProvider;
import com.queekus.pizzachef.data.models.ModItemModelProvider;
import com.queekus.pizzachef.data.recipes.ModRecipeProvider;
import com.queekus.pizzachef.data.tags.ModBlockTagProvider;
import com.queekus.pizzachef.data.tags.ModItemTagProvider;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = PizzaChef.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
    public static final String[] LOCALES_BUILTIN = {
        "en_us"
    };

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) throws InterruptedException
    {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gatherTags(dataGenerator, existingFileHelper);
        gatherRecipes(dataGenerator);
        gatherLocales(dataGenerator);
        gatherModels(dataGenerator, existingFileHelper);
        gatherLoot(dataGenerator);
    }

    private static void gatherLoot(DataGenerator dataGenerator)
    {
        dataGenerator.addProvider(new ModLootTableProvider(dataGenerator));
    }

    private static void gatherModels(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) throws InterruptedException
    {
        dataGenerator.addProvider(new ModBlockStateProvider(dataGenerator, existingFileHelper));
        dataGenerator.addProvider(new ModItemModelProvider(dataGenerator, existingFileHelper));
    }

    private static void gatherLocales(DataGenerator dataGenerator)
    {
        for (String locale : LOCALES_BUILTIN)
            dataGenerator.addProvider(new ModLangProvider(dataGenerator, locale));
    }

    private static void gatherRecipes(DataGenerator dataGenerator)
    {
        dataGenerator.addProvider(new ModRecipeProvider(dataGenerator));
    }

    private static void gatherTags(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper)
    {
        ModBlockTagProvider blockTagGenerator = new ModBlockTagProvider(dataGenerator, existingFileHelper);

        dataGenerator.addProvider(blockTagGenerator);
        dataGenerator.addProvider(new ModItemTagProvider(dataGenerator, blockTagGenerator, existingFileHelper));
    }
}
