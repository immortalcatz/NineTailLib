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
import keri.ninetaillib.lib.util.IUpdateCallback;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemToolHelper {

    private CreativeTabs creativeTab;

    public ItemToolHelper(CreativeTabs creativeTab){
        this.creativeTab = creativeTab;
    }

    public Item createSword(Item.ToolMaterial material){
        return new ItemSwordCustom(material, null).setCreativeTab(this.creativeTab);
    }

    public Item createSword(Item.ToolMaterial material, IUpdateCallback updateCallback){
        return new ItemSwordCustom(material, updateCallback).setCreativeTab(this.creativeTab);
    }

    public Item createPickaxe(Item.ToolMaterial material){
        return new ItemPickaxeCustom(material, null).setCreativeTab(this.creativeTab);
    }

    public Item createPickaxe(Item.ToolMaterial material, IUpdateCallback updateCallback){
        return new ItemPickaxeCustom(material, updateCallback).setCreativeTab(this.creativeTab);
    }

    public Item createShovel(Item.ToolMaterial material){
        return new ItemShovelCustom(material, null).setCreativeTab(this.creativeTab);
    }

    public Item createShovel(Item.ToolMaterial material, IUpdateCallback updateCallback){
        return new ItemShovelCustom(material, updateCallback).setCreativeTab(this.creativeTab);
    }

    public Item createAxe(Item.ToolMaterial material){
        return new ItemAxeCustom(material, null).setCreativeTab(this.creativeTab);
    }

    public Item createAxe(Item.ToolMaterial material, IUpdateCallback updateCallback){
        return new ItemAxeCustom(material, updateCallback).setCreativeTab(this.creativeTab);
    }

    public Item createHoe(Item.ToolMaterial material){
        return new ItemHoeCustom(material, null).setCreativeTab(this.creativeTab);
    }

    public Item createHoe(Item.ToolMaterial material, IUpdateCallback updateCallback){
        return new ItemHoeCustom(material, updateCallback).setCreativeTab(this.creativeTab);
    }

    private class ItemSwordCustom extends ItemSword implements IContentRegister, IItemRenderTypeProvider, IIconItem {

        private String modid;
        private String itemName;
        private IUpdateCallback updateCallback;
        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        public ItemSwordCustom(ToolMaterial material, IUpdateCallback updateCallback){
            super(material);
            this.itemName = "sword_" + material.name().toLowerCase();
            this.updateCallback = updateCallback;
        }

        @Override
        public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
            if(this.updateCallback != null){
                ((IUpdateCallback)this).update(stack, world, entity, itemSlot, isSelected);
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
        public void registerIcons(IIconRegister register) {
            this.texture = register.registerIcon(this.modid + ":items/tool/" + this.itemName);
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
        public EnumItemRenderType getRenderType() {
            return RenderItems.DEFAULT_TOOL;
        }

    }

    private class ItemPickaxeCustom extends ItemPickaxe implements IContentRegister, IItemRenderTypeProvider, IIconItem {

        private String modid;
        private String itemName;
        private IUpdateCallback updateCallback;
        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        public ItemPickaxeCustom(ToolMaterial material, IUpdateCallback updateCallback){
            super(material);
            this.itemName = "pickaxe_" + material.name().toLowerCase();
            this.updateCallback = updateCallback;
        }

        @Override
        public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
            if(this.updateCallback != null){
                ((IUpdateCallback)this).update(stack, world, entity, itemSlot, isSelected);
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
        public void registerIcons(IIconRegister register) {
            this.texture = register.registerIcon(this.modid + ":items/tool/" + this.itemName);
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
        public EnumItemRenderType getRenderType() {
            return RenderItems.DEFAULT_TOOL;
        }

    }

    private class ItemShovelCustom extends ItemSpade implements IContentRegister, IItemRenderTypeProvider, IIconItem {

        private String modid;
        private String itemName;
        private IUpdateCallback updateCallback;
        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        public ItemShovelCustom(ToolMaterial material, IUpdateCallback updateCallback){
            super(material);
            this.itemName = "shovel_" + material.name().toLowerCase();
            this.updateCallback = updateCallback;
        }

        @Override
        public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
            if(this.updateCallback != null){
                ((IUpdateCallback)this).update(stack, world, entity, itemSlot, isSelected);
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
        public void registerIcons(IIconRegister register) {
            this.texture = register.registerIcon(this.modid + ":items/tool/" + this.itemName);
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
        public EnumItemRenderType getRenderType() {
            return RenderItems.DEFAULT_TOOL;
        }

    }

    private class ItemAxeCustom extends ItemAxe implements IContentRegister, IItemRenderTypeProvider, IIconItem {

        private String modid;
        private String itemName;
        private IUpdateCallback updateCallback;
        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        public ItemAxeCustom(ToolMaterial material, IUpdateCallback updateCallback){
            super(material, material.getDamageVsEntity(), material.getEfficiencyOnProperMaterial());
            this.itemName = "axe_" + material.name().toLowerCase();
            this.updateCallback = updateCallback;
        }

        @Override
        public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
            if(this.updateCallback != null){
                ((IUpdateCallback)this).update(stack, world, entity, itemSlot, isSelected);
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
        public void registerIcons(IIconRegister register) {
            this.texture = register.registerIcon(this.modid + ":items/tool/" + this.itemName);
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
        public EnumItemRenderType getRenderType() {
            return RenderItems.DEFAULT_TOOL;
        }

    }

    private class ItemHoeCustom extends ItemHoe implements IContentRegister, IItemRenderTypeProvider, IIconItem {

        private String modid;
        private String itemName;
        private IUpdateCallback updateCallback;
        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        public ItemHoeCustom(ToolMaterial material, IUpdateCallback updateCallback){
            super(material);
            this.itemName = "hoe_" + material.name().toLowerCase();
            this.updateCallback = updateCallback;
        }

        @Override
        public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
            if(this.updateCallback != null){
                this.updateCallback.update(stack, world, entity, itemSlot, isSelected);
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
        public void registerIcons(IIconRegister register) {
            this.texture = register.registerIcon(this.modid + ":items/tool/" + this.itemName);
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
        public EnumItemRenderType getRenderType() {
            return RenderItems.DEFAULT_TOOL;
        }

    }

}
