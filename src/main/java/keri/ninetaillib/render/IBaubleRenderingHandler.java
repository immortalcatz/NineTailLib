package keri.ninetaillib.render;

import baubles.api.render.IRenderBauble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IBaubleRenderingHandler {

    Item getItem();

    void renderBauble(ItemStack stack, EntityPlayer player, float partialTicks, IRenderBauble.RenderType type);

}
