package com.queekus.pizzachef.api;

import com.queekus.pizzachef.tileentities.TileEntityGranitePizzaSlab;

public enum PizzaSide
{
    LEFT(0.5d, TileEntityGranitePizzaSlab.getPizzaLeftSlots()),
    RIGHT(0.1d, TileEntityGranitePizzaSlab.getPizzaRightSlots());

    public final double offsetX;
    public final int[] slots;

    PizzaSide(double offsetX, int[] slots)
    {
        this.offsetX = offsetX;
        this.slots = slots;
    }
}