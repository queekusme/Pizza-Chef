package com.queekus.pizzachef.data.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModLocaleEntry<T>
{
    T holder;
    Map<String, String> localeData = new HashMap<>();

    public static <T> ModLocaleEntry<T> create(T holder)
    {
        return new ModLocaleEntry<T>(holder);
    }

    public static <T> ModLocaleEntry<T> create(T holder, Consumer<ModLocaleEntry<T>> consumer)
    {
        return create(holder).updateLocaleData(consumer);
    }

    protected ModLocaleEntry(T holder)
    {
        this.holder = holder;
    }

    public ModLocaleEntry<T> updateLocaleData(Consumer<ModLocaleEntry<T>> consumer)
    {
        consumer.accept(this);

        return this;
    }

    public void addEntry(String locale, String translation)
    {
        this.localeData.put(locale, translation);
    }

    public void register(BiConsumer<T, String> consumer, String locale)
    {
        consumer.accept(holder, this.getLocaleEntryFor(locale));
    }

    private String getLocaleEntryFor(String locale) { return localeData.get(locale); }
}
