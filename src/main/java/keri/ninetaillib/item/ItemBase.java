package keri.ninetaillib.item;

import codechicken.lib.util.TransformUtils;
import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.render.DefaultItemRenderer;
import keri.ninetaillib.render.IItemRenderingHandler;
import keri.ninetaillib.texture.IIconItem;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.ninetaillib.util.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBase extends Item implements IIconItem {

    private String itemName;
    private String modid;
    private String[] subNames = null;

    public ItemBase(String modid, String itemName) {
        this.itemName = itemName;
        this.modid = modid;
        this.setRegistryName(modid, itemName);
        this.setUnlocalizedName(modid + "." + itemName);

        if(super.getClass().isAnnotationPresent(HideInventory.class)){
            HideInventory annotation = super.getClass().getAnnotation(HideInventory.class);

            if(!annotation.onlySubtypes()){
                this.setCreativeTab((CreativeTabs) null);
            }
        }
        else{
            this.setCreativeTab(this.getCreativeTab());
        }

        NineTailLib.PROXY.handleItem(this);
        GameRegistry.register(this);
    }

    public ItemBase(String modid, String itemName, String... subNames) {
        this.itemName = itemName;
        this.modid = modid;
        this.subNames = subNames;
        this.setRegistryName(modid, itemName);
        this.setUnlocalizedName(modid + "." + itemName);
        this.setHasSubtypes(true);

        if(super.getClass().isAnnotationPresent(HideInventory.class)){
            HideInventory annotation = super.getClass().getAnnotation(HideInventory.class);

            if(!annotation.onlySubtypes()){
                this.setCreativeTab((CreativeTabs)null);
            }
        }
        else{
            this.setCreativeTab(this.getCreativeTab());
        }

        NineTailLib.PROXY.handleItem(this);
        GameRegistry.register(this);
    }

    @Override
    public int getMetadata(int damage) {
        if(this.subNames != null){
            return damage;
        }
        else{
            return 0;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
        if(this.subNames != null){
            if(super.getClass().isAnnotationPresent(HideInventory.class)){
                HideInventory annotation = super.getClass().getAnnotation(HideInventory.class);

                if(annotation.onlySubtypes()){
                    subItems.add(new ItemStack(item, 1, 0));
                }
                else{
                    for(int i = 0; i < this.subNames.length; i++){
                        subItems.add(new ItemStack(item, 1, i));
                    }
                }
            }
            else{
                for(int i = 0; i < this.subNames.length; i++){
                    subItems.add(new ItemStack(item, 1, i));
                }
            }
        }
        else{
            subItems.add(new ItemStack(item, 1, 0));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) {
        if(this instanceof ICustomLocalization){
            ICustomLocalization iface = (ICustomLocalization)this;
            return iface.getUnlocalizedNameCustom();
        }
        else{
            if(this.subNames != null){
                return this.getUnlocalizedName() + "." + this.subNames[stack.getMetadata()];
            }
            else{
                return this.getUnlocalizedName();
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if(this instanceof IShiftDescription){
            IShiftDescription iface = (IShiftDescription)this;

            if(CommonUtils.isShiftPressed()){
                iface.addDescription(stack, player, tooltip);
            }
            else{
                tooltip.add(LanguageUtils.PRESS_KEY + " " + LanguageUtils.KEY_SHIFT + " " + LanguageUtils.SHOW_INFO);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegistrar registrar) {
        if(this.getSubNames() != null){
            this.texture = new TextureAtlasSprite[this.subNames.length];

            for(int i = 0; i < this.subNames.length; i++){
                this.texture[i] = registrar.registerIcon(this.modid + ":items/" + this.itemName + "_" + this.subNames[i]);
            }
        }
        else{
            this.texture = new TextureAtlasSprite[1];
            this.texture[0] = registrar.registerIcon(this.modid + ":items/" + this.itemName);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta) {
        return this.texture[meta];
    }

    public String getInternalName(){
        return this.itemName;
    }

    public String[] getSubNames(){
        return this.subNames;
    }

    public int getRenderType(){
        return 0;
    }

    @Override
    public CreativeTabs getCreativeTab(){
        return CreativeTabs.SEARCH;
    }

    public IItemRenderingHandler getRenderingHandler(){
        return new DefaultItemRenderer(TransformUtils.DEFAULT_ITEM);
    }

    public String getModId(){
        return this.modid;
    }

}
