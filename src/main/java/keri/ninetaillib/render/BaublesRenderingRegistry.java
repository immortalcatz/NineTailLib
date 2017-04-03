package keri.ninetaillib.render;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public class BaublesRenderingRegistry {

    public static final BaublesRenderingRegistry INSTANCE = new BaublesRenderingRegistry();
    private List<IBaublesRenderingHandler> renderingHandlers = Lists.newArrayList();

    public void registerRenderingHandler(IBaublesRenderingHandler handler){
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

    public ImmutableList<IBaublesRenderingHandler> getRenderingHandlers(){
        return ImmutableList.copyOf(this.renderingHandlers);
    }

}
