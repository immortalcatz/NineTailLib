package keri.ninetaillib.util;

import com.google.common.collect.Lists;
import keri.ninetaillib.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class StateMapBuilder {

    private BlockBase block;
    private int count;
    private int prevCount;
    private List<ModelResourceLocation> locations = Lists.newArrayList();
    private StringBuilder builder;

    private StateMapBuilder(BlockBase block){
        this.block = block;
        this.count = 0;
        this.prevCount = 0;
        this.builder = new StringBuilder();
    }

    public StateMapBuilder addProperty(String propertyName, String property){
        if(propertyName != null && propertyName != "" && property != null && property != ""){
            if(this.prevCount < this.count){
                this.builder.append(',');
            }

            this.prevCount = this.count;
            this.builder.append(propertyName);
            this.builder.append('=');
            this.builder.append(property);
            this.count++;
        }
        else{
            throw new IllegalArgumentException("Property name can't be null or empty !");
        }

        return this;
    }

    public void endProperty(){
        ResourceLocation rl = new ResourceLocation(this.block.getRegistryName().getResourceDomain(), this.block.getInternalName());
        this.locations.add(new ModelResourceLocation(rl, this.builder.toString()));
        this.count = 0;
        this.prevCount = 0;
        this.builder = new StringBuilder();
    }

    public void applyStateMap(){
        for(ModelResourceLocation location : this.locations){
            ModelLoader.setCustomStateMapper(this.block, new ClientUtils.SimpleStateMapper(location));
        }
    }

    public static StateMapBuilder createBuilder(Block block){
        if(block instanceof BlockBase){
            return new StateMapBuilder((BlockBase)block);
        }
        else{
            throw new IllegalArgumentException("Block must be an instance of BlockBase !");
        }
    }

}
