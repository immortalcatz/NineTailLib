package keri.ninetaillib.render;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class RendererRegistry {

    public static final RendererRegistry INSTANCE = new RendererRegistry();
    private int blockRenderId = 0;
    private int itemRenderId = 0;
    private ArrayList<IBlockRenderingHandler> blockRenderers = Lists.newArrayList();
    private ArrayList<IItemRenderingHandler> itemRenderers = Lists.newArrayList();

    public void registerBlockRenderingHandler(IBlockRenderingHandler handler){
        if(handler != null){
            this.blockRenderers.add(this.blockRenderId, handler);
            this.blockRenderId++;
        }
        else{
            throw new IllegalArgumentException("IBlockRenderingHandler can't be null !");
        }
    }

    public void registerItemRenderingHandler(IItemRenderingHandler handler){
        if(handler != null){
            this.itemRenderers.add(this.itemRenderId, handler);
            this.itemRenderId++;
        }
        else{
            throw new IllegalArgumentException("IItemRenderingHandler can't be null !");
        }
    }

    public int getNextAvailableBlockRenderId() {
        return this.blockRenderId;
    }

    public int getNextAvailableItemRenderId(){
        return this.itemRenderId;
    }

    public IBlockRenderingHandler getBlockRenderingHandlerById(int renderId){
        return this.blockRenderers.get(renderId);
    }

    public IItemRenderingHandler getItemRenderingHandlerById(int renderId){
        return this.itemRenderers.get(renderId);
    }

}
