package com.queekus.pizzachef.tileentities.renderers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.items.ModItems;
import com.queekus.pizzachef.tileentities.TileEntityGranitePizzaSlab;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class GranitePizzaSlabTileEntityRenderer extends TileEntityRenderer<TileEntityGranitePizzaSlab>
{
    public static final ResourceLocation PIZZA_UNCOOKED = new ResourceLocation(PizzaChef.MOD_ID, "textures/model/pizza_uncooked.png");
    public static final ResourceLocation PIZZA_COOKED = new ResourceLocation(PizzaChef.MOD_ID, "textures/model/pizza_cooked.png");

    public GranitePizzaSlabTileEntityRenderer(TileEntityRendererDispatcher dispatch)
    {
        super(dispatch);
    }

    @Override
    public void render(
        TileEntityGranitePizzaSlab tileEntity,
        float partialTicks,
        MatrixStack matrixStackIn,
        IRenderTypeBuffer bufferIn,
        int combinedLightIn,
        int combinedOverlayIn
    )
    {
        if(!tileEntity.hasPizza())
            return;

        ModelRenderer pizzaBody = new ModelRenderer(64, 64, 0, 0);
        pizzaBody.mirror = true;
        pizzaBody.addBox(-6.0F, 0, -6.0F, 12.0F, 1, 12.0F); // Base
        pizzaBody.setPos(8.0F, 1.0F, 8.0F);

        ModelRenderer westCrust = new ModelRenderer(64, 64, 0, 14);
        westCrust.mirror = true;
        westCrust.addBox(-7.0F, 0, -7.0F, 1.0F, 2, 14.0F); // West
        westCrust.setPos(8.0F, 1.0F, 8.0F);

        ModelRenderer northCrust = new ModelRenderer(64, 64, 0, 33);
        northCrust.mirror = true;
        northCrust.addBox(-6.0F, 0, -7.0F, 12.0F, 2, 1.0F); // North
        northCrust.setPos(8.0F, 1.0F, 8.0F);

        ModelRenderer eastCrust = new ModelRenderer(64, 64, 32, 14);
        eastCrust.mirror = true;
        eastCrust.addBox(6.0F, 0, -7.0F, 1.0F, 2, 14.0F); // East
        eastCrust.setPos(8.0F, 1.0F, 8.0F);

        ModelRenderer southCrust = new ModelRenderer(64, 64, 32, 33);
        southCrust.mirror = true;
        southCrust.addBox(-6.0F, 0, 6.0F, 12.0F, 2, 1.0F); // South
        southCrust.setPos(8.0F, 1.0F, 8.0F);

        IVertexBuilder renderBuffer = bufferIn.getBuffer(RenderType.entitySolid(this.getTextureForPizzaKind(tileEntity.getPizza().getItem())));
        pizzaBody.render(matrixStackIn, renderBuffer, combinedLightIn, combinedOverlayIn);
        westCrust.render(matrixStackIn, renderBuffer, combinedLightIn, combinedOverlayIn);
        northCrust.render(matrixStackIn, renderBuffer, combinedLightIn, combinedOverlayIn);
        eastCrust.render(matrixStackIn, renderBuffer, combinedLightIn, combinedOverlayIn);
        southCrust.render(matrixStackIn, renderBuffer, combinedLightIn, combinedOverlayIn);
    }

    private ResourceLocation getTextureForPizzaKind(Item pizzaItem)
    {
        if(pizzaItem == ModItems.pizza)
            return PIZZA_COOKED;

        return PIZZA_UNCOOKED;
    }
}
