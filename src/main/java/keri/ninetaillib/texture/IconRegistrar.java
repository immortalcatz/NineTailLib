package keri.ninetaillib.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class IconRegistrar implements IIconRegistrar {

    public static final IconRegistrar INSTANCE = new IconRegistrar();
    private ArrayList<IIconBlock> blocksToRegister = Lists.newArrayList();
    private ArrayList<IIconItem> itemsToRegister = Lists.newArrayList();
    private Map<String, ResourceLocation> locationMap = Maps.newHashMap();
    private Map<String, TextureAtlasSprite> iconMap = Maps.newHashMap();

    @Override
    public TextureAtlasSprite registerIcon(String path){
        if(!this.locationMap.containsKey(path)){
            this.locationMap.put(path, new ResourceLocation(path));
        }

        return this.iconMap.get(path);
    }

    @SubscribeEvent
    public void onTextureStitchPre(TextureStitchEvent.Pre event){
        for(IIconBlock block : this.blocksToRegister){
            block.registerBlockIcons(INSTANCE);
        }

        for(IIconItem item : this.itemsToRegister){
            item.registerIcons(INSTANCE);
        }

        for(Map.Entry<String, ResourceLocation> entry : this.locationMap.entrySet()){
            TextureAtlasSprite icon = event.getMap().registerSprite(entry.getValue());
            this.iconMap.put(entry.getKey(), icon);
        }
    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event){
        for(IIconBlock block : this.blocksToRegister){
            block.registerBlockIcons(INSTANCE);
        }

        for(IIconItem item : this.itemsToRegister){
            item.registerIcons(INSTANCE);
        }
    }

    public void registerBlock(IIconBlock block){
        if(block != null){
            this.blocksToRegister.add(block);
        }
    }

    public void registerItem(IIconItem item){
        if(item != null){
            this.itemsToRegister.add(item);
        }
    }

}
