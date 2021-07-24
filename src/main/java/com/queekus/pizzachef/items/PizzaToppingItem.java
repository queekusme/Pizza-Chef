package com.queekus.pizzachef.items;

import com.queekus.pizzachef.api.IPizzaTopping;

import net.minecraft.world.item.Item;

public class PizzaToppingItem extends Item implements IPizzaTopping
{
    final float tesrScale;
    public PizzaToppingItem(Properties props, float tesrScale)
    {
        super(props);
        this.tesrScale = tesrScale;
    }

    @Override
    public float getTESRScale()
    {
        return tesrScale;
    }
}
