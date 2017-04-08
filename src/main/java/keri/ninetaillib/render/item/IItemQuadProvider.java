package keri.ninetaillib.render.item;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IItemQuadProvider {

    List<BakedQuad> getQuads(ItemStack stack, long random);

}
