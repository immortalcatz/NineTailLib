package keri.ninetaillib.util;

import codechicken.lib.model.ModelRegistryHelper;
import com.google.common.collect.Lists;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.render.model.SimpleBakedModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MLESupressor {

    private Block block;

    public MLESupressor(Block block){
        this.block = block;
    }

    public void supress(boolean supressItemMLE, boolean supressType){
        if(supressType){
            IMetaBlock metaBlock = (IMetaBlock)this.block;

            for(int i = 0; i < metaBlock.getSubNames().length; i++){
                ModelResourceLocation location = new ModelResourceLocation(this.block.getRegistryName(), "type=" + metaBlock.getSubNames()[i]);
                ModelLoader.setCustomStateMapper(this.block, new SimpleStateMapper(location));
                ModelRegistryHelper.register(location, new SimpleBakedModel(Lists.newArrayList()));
            }
        }
        else{
            ModelResourceLocation location = new ModelResourceLocation(this.block.getRegistryName(), "default");
            ModelLoader.setCustomStateMapper(this.block, new SimpleStateMapper(location));
            ModelRegistryHelper.register(location, new SimpleBakedModel(Lists.newArrayList()));
        }

        if(supressItemMLE){
            ModelResourceLocation locationItem = new ModelResourceLocation(this.block.getRegistryName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this.block), 0, locationItem);
            ModelRegistryHelper.register(locationItem, new SimpleBakedModel(Lists.newArrayList()));
        }
    }

}
