package keri.ninetaillib.item;

import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.texture.IIconItem;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.ninetaillib.util.HideInventory;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFoodBase extends ItemFood implements IIconItem {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite texture;
    private String modid;
    private String itemName;

    public ItemFoodBase(String modid, String itemName, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.modid = modid;
        this.itemName = itemName;
        this.register();
    }

    public ItemFoodBase(String modid, String itemName, int amount, boolean isWolfFood) {
        super(amount, isWolfFood);
        this.modid = modid;
        this.itemName = itemName;
        this.register();
    }

    private void register(){
        this.setRegistryName(this.modid, this.itemName);
        this.setUnlocalizedName(this.modid + "." + this.itemName);

        if(super.getClass().isAnnotationPresent(HideInventory.class)){
            HideInventory annotation = super.getClass().getAnnotation(HideInventory.class);

            if(!annotation.onlySubtypes()){
                this.setCreativeTab((CreativeTabs)null);
            }
        }
        else{
            this.setCreativeTab(this.getCreativeTab());
        }

        NineTailLib.PROXY.handleItemSpecial(this);
        GameRegistry.register(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegistrar registrar) {
        this.texture = registrar.registerIcon(this.modid + ":items/food/" + this.itemName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta) {
        return this.texture;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.FOOD;
    }

    public String getModId(){
        return this.modid;
    }

    public String getInternalName(){
        return this.itemName;
    }

}
