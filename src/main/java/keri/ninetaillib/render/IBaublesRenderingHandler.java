package keri.ninetaillib.render;

import baubles.api.render.IRenderBauble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;

public interface IBaublesRenderingHandler {

    Item getItem();

    void renderBauble(IInventory inventory, EntityPlayer player, float partialTicks, IRenderBauble.RenderType type);

}
