package keri.ninetaillib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ResourceAction {

    private ResourceLocation location;

    public ResourceAction(ResourceLocation location){
        this.location = location;
    }

    public ResourceAction(String path){
        this.location = new ResourceLocation(path);
    }

    public ResourceAction(String modid, String path){
        this.location = new ResourceLocation(modid, path);
    }

    public void bind(boolean useManager){
        if(useManager){
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.location);
        }
        else{
            Minecraft.getMinecraft().renderEngine.bindTexture(this.location);
        }
    }

}
