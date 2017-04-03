package keri.ninetaillib.render;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public class BaubleRenderingRegistry {

    public static final BaubleRenderingRegistry INSTANCE = new BaubleRenderingRegistry();
    private List<IBaubleRenderingHandler> renderingHandlers = Lists.newArrayList();

    public void registerRenderingHandler(IBaubleRenderingHandler handler){
        if(handler != null){
            if(handler.getItem() != null){
                this.renderingHandlers.add(handler);
            }
            else{
                throw new IllegalArgumentException("Item cannot be null !");
            }
        }
        else{
            throw new IllegalArgumentException("Rendering handler cannot be null !");
        }
    }

    public ImmutableList<IBaubleRenderingHandler> getRenderingHandlers(){
        return ImmutableList.copyOf(this.renderingHandlers);
    }

}
