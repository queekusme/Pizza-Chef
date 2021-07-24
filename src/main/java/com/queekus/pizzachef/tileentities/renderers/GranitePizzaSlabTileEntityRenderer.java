package com.queekus.pizzachef.tileentities.renderers;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.queekus.pizzachef.PizzaChef;
import com.queekus.pizzachef.api.IPizzaTopping;
import com.queekus.pizzachef.api.PizzaSide;
import com.queekus.pizzachef.items.ModItems;
import com.queekus.pizzachef.tileentities.TileEntityGranitePizzaSlab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

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

        renderSide(tileEntity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, PizzaSide.LEFT);
        renderSide(tileEntity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, PizzaSide.RIGHT);

        renderPizza(tileEntity, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    private void renderSide(
        TileEntityGranitePizzaSlab tileEntity,
        float partialTicks,
        MatrixStack matrixStackIn,
        IRenderTypeBuffer bufferIn,
        int combinedLightIn,
        int combinedOverlayIn,
        PizzaSide side
    )
    {
        Random random = new Random(1);
        for(int i = 0; i < side.slots.length; i++)
        {
            ItemStack ingredient = tileEntity.getItem(side.slots[i]);
            if (ingredient != ItemStack.EMPTY)
            {
                renderTopping(tileEntity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, side, ingredient, i, random);
            }
        }
    }

    private void renderTopping(
        TileEntityGranitePizzaSlab tileEntity,
        float partialTicks,
        MatrixStack matrixStackIn,
        IRenderTypeBuffer bufferIn,
        int combinedLightIn,
        int combinedOverlayIn,
        PizzaSide side,
        ItemStack topping,
        int layer,
        Random random
    )
    {
        for(int j=0; j < 3; j++)
        {
            for(int k=0; k < 8; k++)
            {
                double xPos = side.offsetX + (j + 0.5) * 0.125d;
                double yPos = (1d/128 * layer) + 0.125;
                double zPos = 0.13D + (0.09 * k);

                Item toppingItem = topping.getItem();
                float scale = (toppingItem instanceof IPizzaTopping) ? ((IPizzaTopping)toppingItem).getTESRScale() : 0.1f;

                matrixStackIn.pushPose();
                matrixStackIn.translate(xPos + (random.nextFloat() * (1 / 16f)), yPos, zPos + (random.nextFloat() * (1 / 16f)));
                matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360));
                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                matrixStackIn.scale(scale, scale, scale);
                Minecraft.getInstance().getItemRenderer().renderStatic(topping, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
                matrixStackIn.popPose();
            }
        }
    }

    private void renderPizza(
        TileEntityGranitePizzaSlab tileEntity,
        MatrixStack matrixStackIn,
        IRenderTypeBuffer bufferIn,
        int combinedLightIn,
        int combinedOverlayIn
    )
    {
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
