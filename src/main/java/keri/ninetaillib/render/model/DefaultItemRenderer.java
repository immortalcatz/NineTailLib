package keri.ninetaillib.render.model;

import codechicken.lib.render.CCModelState;
import codechicken.lib.render.CCRenderState;
import keri.ninetaillib.render.registry.IItemRenderingHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DefaultItemRenderer implements IItemRenderingHandler {

    /**
     * MORE TODO IN HERE YA' LAZY FUCKTURD
     */

    private CCModelState itemTransforms;

    public DefaultItemRenderer(CCModelState transforms){
        this.itemTransforms = transforms;
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack) {

    }

}
