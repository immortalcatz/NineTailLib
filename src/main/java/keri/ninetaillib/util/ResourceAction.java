package keri.ninetaillib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ResourceAction extends ResourceLocation {

    protected ResourceAction(int unused, String... resourceName) {
        super(unused, resourceName);
    }

    public ResourceAction(String path) {
        super(path);
    }

    public ResourceAction(String domain, String path) {
        super(domain, path);
    }

    public void bind(boolean useManager){
        if(useManager){
            Minecraft.getMinecraft().getTextureManager().bindTexture(this);
        }
        else{
            Minecraft.getMinecraft().renderEngine.bindTexture(this);
        }
    }

}
