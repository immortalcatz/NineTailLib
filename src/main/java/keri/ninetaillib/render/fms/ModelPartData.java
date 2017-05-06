package keri.ninetaillib.render.fms;

import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Transformation;
import codechicken.lib.vec.uv.UVTransformation;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ModelPartData {

    private Cuboid6 bounds;
    private ResourceLocation[] texture;
    private boolean hasSpecialTexture;
    private TextureAtlasSprite[] textureSpecial;
    private int color;
    private boolean hasBrightnessOverride;
    private int brightness;
    private List<Transformation> transformations;
    private List<UVTransformation> uvTransformations;

    public ModelPartData(){
        this.bounds = new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D);
        this.texture = new ResourceLocation[6];
        this.hasSpecialTexture = false;
        this.textureSpecial = new TextureAtlasSprite[6];
        this.color = 0xFFFFFFFF;
        this.hasBrightnessOverride = false;
        this.brightness = 0;
        this.transformations = Lists.newArrayList();
        this.uvTransformations = Lists.newArrayList();
    }

    public void setBounds(Cuboid6 bounds){ this.bounds = bounds; }

    public void setTexture(ResourceLocation[] texture){ this.texture = texture; }

    public void setHasSpecialTexture(boolean hasSpecialTexture){ this.hasSpecialTexture = hasSpecialTexture; }

    public void setTextureSpecial(TextureAtlasSprite[] texture){ this.textureSpecial = texture; }

    public void setColor(int color){ this.color = color; }

    public void setHasBrightnessOverride(boolean hasBrightnessOverride){ this.hasBrightnessOverride = hasBrightnessOverride; }

    public void setBrightness(int brightness){ this.brightness = brightness; }

    public void addTransformation(Transformation transformation){ this.transformations.add(transformation); }

    public void addUVTransformation(UVTransformation transformation){ this.uvTransformations.add(transformation); }

    public Cuboid6 getBounds(){ return this.bounds; }

    public ResourceLocation[] getTexture(){ return this.texture; }

    public boolean getHasSpecialTexture(){ return this.hasSpecialTexture; }

    public TextureAtlasSprite[] getTextureSpecial(){ return this.textureSpecial; }

    public int getColor(){ return this.color; }

    public boolean getHasBrightnessOverride(){ return this.hasBrightnessOverride; }

    public int getBrightness(){ return this.brightness; }

    public List<Transformation> getTransformations(){ return this.transformations; }

    public List<UVTransformation> getUVTransformations(){ return this.uvTransformations; }

}
