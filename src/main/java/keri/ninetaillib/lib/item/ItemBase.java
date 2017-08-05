/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.item;

import keri.ninetaillib.lib.mod.IContentRegister;
import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.render.IItemRenderTypeProvider;
import keri.ninetaillib.lib.render.RenderItems;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.texture.IIconItem;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.IShiftDescription;
import keri.ninetaillib.lib.util.Translations;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemBase extends Item implements IContentRegister, IItemRenderTypeProvider, IIconItem {

    private String modid;
    private String itemName;
    private String[] subNames;
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public ItemBase(String itemName) {
        this.itemName = itemName;
        this.subNames = null;
        this.setCreativeTab(this.getCreativeTab());
    }

    public ItemBase(String itemName, String... subNames){
        this.itemName = itemName;
        this.subNames = subNames;
        this.setCreativeTab(this.getCreativeTab());
        this.setHasSubtypes(true);
    }

    @Override
    public int getDamage(ItemStack stack) {
        if(this.subNames != null){
            return stack.getMetadata();
        }
        else{
            return super.getDamage(stack);
        }
    }

    @Override
    public CreativeTabs getCreativeTab(){
        return CreativeTabs.MISC;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if(this.subNames != null){
            for(int i = 0; i < this.subNames.length; i++){
                subItems.add(new ItemStack(this, 1, i));
            }
        }
        else{
            subItems.add(new ItemStack(this, 1, 0));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) {
        if(this.subNames != null){
            return this.getUnlocalizedName() + "." + this.subNames[stack.getMetadata()];
        }
        else{
            return super.getUnlocalizedName(stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        if(this.subNames != null){
            this.texture = new TextureAtlasSprite[this.subNames.length];

            for(int i = 0; i < this.subNames.length; i++){
                this.texture[i] = register.registerIcon(this.modid + ":items/" + this.itemName + "_" + this.subNames[i]);
            }
        }
        else{
            this.texture = new TextureAtlasSprite[1];
            this.texture[0] = register.registerIcon(this.modid + ":items/" + this.itemName);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta) {
        return this.texture[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(ItemStack stack) {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if(this instanceof IShiftDescription){
            if(((IShiftDescription)this).shouldAddDescription(stack, player)){
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
                    ((IShiftDescription)this).addDescription(stack, player, tooltip);
                }
                else{
                    String press = TextFormatting.GRAY + Translations.TOOLTIP_PRESS;
                    String shift = TextFormatting.YELLOW + Translations.TOOLTIP_SHIFT;
                    String info = TextFormatting.GRAY + Translations.TOOLTIP_INFO;
                    tooltip.add(press + " " + shift + " " + info);
                }
            }
        }
    }

    @Override
    public void handlePreInit(FMLPreInitializationEvent event) {
        this.modid = event.getModMetadata().modId;
        this.setRegistryName(this.modid, this.itemName);
        this.setUnlocalizedName(this.modid + "." + this.itemName);
        GameRegistry.register(this);
    }

    @Override
    public void handleInit(FMLInitializationEvent event) {

    }

    @Override
    public void handlePostInit(FMLPostInitializationEvent event) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientPreInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerItem(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientInit(FMLInitializationEvent event) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientPostInit(FMLPostInitializationEvent event) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumItemRenderType getRenderType(){
        return RenderItems.DEFAULT_ITEM;
    }

    public String getModid(){
        return this.modid;
    }

    public String getItemName(){
        return this.itemName;
    }

    public String[] getSubNames(){
        return this.subNames;
    }

}
