/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
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
import keri.ninetaillib.lib.util.TranslationUtils;
import keri.ninetaillib.mod.util.ModPrefs;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFoodBase extends ItemFood implements IContentRegister, IItemRenderTypeProvider, IIconItem {

    private String modid;
    private String itemName;
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite texture;

    public ItemFoodBase(String itemName, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.itemName = itemName;
        this.setCreativeTab(this.getCreativeTab());
    }

    public ItemFoodBase(String itemName, int amount, boolean isWolfFood) {
        super(amount, isWolfFood);
        this.itemName = itemName;
        this.setCreativeTab(this.getCreativeTab());
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

    @Nullable
    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.FOOD;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = register.registerIcon(this.modid + ":items/" + this.itemName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta) {
        return this.texture;
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
                    String press = TextFormatting.GRAY + TranslationUtils.translate(ModPrefs.MODID, "tooltip", "press");
                    String shift = TextFormatting.YELLOW + TranslationUtils.translate(ModPrefs.MODID, "tooltip", "shift");
                    String info = TextFormatting.GRAY + TranslationUtils.translate(ModPrefs.MODID, "tooltip", "info");
                    tooltip.add(press + " " + shift + " " + info);
                }
            }
        }
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

}
