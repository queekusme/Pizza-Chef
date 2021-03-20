package com.queekus.pizzachef.tileentities.renderers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.tileentities.TileEntityGranitePizzaSlab;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
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

        ModelRenderer pizzaBody = new ModelRenderer(32, 32, 0, 0);
        pizzaBody.addBox(-6.0F, 0, -6.0F, 12.0F, 1, 12.0F); // Base
        pizzaBody.addBox(-7.0F, 0, -7.0F, 1.0F, 2, 14.0F); // West
        pizzaBody.addBox(-6.0F, 0, -7.0F, 12.0F, 2, 1.0F); // North
        pizzaBody.addBox(6.0F, 0, -7.0F, 1.0F, 2, 14.0F); // East
        pizzaBody.addBox(-6.0F, 0, 6.0F, 12.0F, 2, 1.0F); // South
        pizzaBody.setPos(8.0F, 1.0F, 8.0F);

        IVertexBuilder renderBuffer = bufferIn.getBuffer(RenderType.entitySolid(PIZZA_UNCOOKED));
        pizzaBody.render(matrixStackIn, renderBuffer, combinedLightIn, combinedOverlayIn);
    }
}
