package com.queekus.pizzachef.loot_modifiers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootSerializers;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class AdditionalItemsLootModifier extends LootModifier
{
    private static final Gson GSON_INSTANCE = LootSerializers.createFunctionSerializer().create();

    Item[] additionalItems;
    public AdditionalItemsLootModifier(ILootCondition[] conditionsIn, Item[] additionalItems)
    {
        super(conditionsIn);
        this.additionalItems = additionalItems;
    }

    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
    {
        generatedLoot.add(new ItemStack(this.additionalItems[context.getLevel().random.nextInt(this.additionalItems.length)]));

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AdditionalItemsLootModifier>
    {
        @Override
        public AdditionalItemsLootModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn)
        {
            List<Item> items = new ArrayList<>();
            JSONUtils.getAsJsonArray(object, "additionalItems").forEach((id) ->
            {
                items.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(id.getAsString())));
            });

            return new AdditionalItemsLootModifier(conditionsIn, items.toArray(new Item[items.size()]));
        }

        @Override
        public JsonObject write(AdditionalItemsLootModifier instance)
        {
            JsonObject out = new JsonObject();

            JsonArray additionalItems = new JsonArray();
            Arrays.asList(instance.additionalItems).stream().forEach((item) -> additionalItems.add(item.getRegistryName().toString()));
            out.add("additionalItems", additionalItems);

            JsonElement conditions = GSON_INSTANCE.toJsonTree(instance.conditions);
            out.add("conditions", conditions);

            return out;
        }
    }
}
