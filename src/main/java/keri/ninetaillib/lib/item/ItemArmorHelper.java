/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.item;

import keri.ninetaillib.lib.mod.IContentRegister;
import keri.ninetaillib.lib.render.*;
import keri.ninetaillib.lib.texture.IIconItem;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.IUpdateCallback;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorHelper {

    private CreativeTabs creativeTab;

    public ItemArmorHelper(CreativeTabs creativeTab){
        this.creativeTab = creativeTab;
    }

    public Item createHelmet(ItemArmor.ArmorMaterial material){
        return new ItemArmorCustom(material, EntityEquipmentSlot.HEAD, null, null).setCreativeTab(this.creativeTab);
    }

    public Item createHelmet(ItemArmor.ArmorMaterial material, IArmorModelProvider modelProvider){
        return new ItemArmorCustom(material, EntityEquipmentSlot.HEAD, null, modelProvider).setCreativeTab(this.creativeTab);
    }

    public Item createHelmet(ItemArmor.ArmorMaterial material, IUpdateCallback updateCallback){
        return new ItemArmorCustom(material, EntityEquipmentSlot.HEAD, updateCallback, null).setCreativeTab(this.creativeTab);
    }

    public Item createHelmet(ItemArmor.ArmorMaterial material, IUpdateCallback updateCallback, IArmorModelProvider modelProvider){
        return new ItemArmorCustom(material, EntityEquipmentSlot.HEAD, updateCallback, modelProvider).setCreativeTab(this.creativeTab);
    }

    public Item createChestplate(ItemArmor.ArmorMaterial material){
        return new ItemArmorCustom(material, EntityEquipmentSlot.CHEST, null, null).setCreativeTab(this.creativeTab);
    }

    public Item createChestplate(ItemArmor.ArmorMaterial material, IArmorModelProvider modelProvider){
        return new ItemArmorCustom(material, EntityEquipmentSlot.CHEST, null, modelProvider).setCreativeTab(this.creativeTab);
    }

    public Item createChestplate(ItemArmor.ArmorMaterial material, IUpdateCallback updateCallback){
        return new ItemArmorCustom(material, EntityEquipmentSlot.CHEST, updateCallback, null).setCreativeTab(this.creativeTab);
    }

    public Item createChestplate(ItemArmor.ArmorMaterial material, IUpdateCallback updateCallback, IArmorModelProvider modelProvider){
        return new ItemArmorCustom(material, EntityEquipmentSlot.CHEST, updateCallback, modelProvider).setCreativeTab(this.creativeTab);
    }

    public Item createLeggings(ItemArmor.ArmorMaterial material){
        return new ItemArmorCustom(material, EntityEquipmentSlot.LEGS, null, null).setCreativeTab(this.creativeTab);
    }

    public Item createLeggings(ItemArmor.ArmorMaterial material, IArmorModelProvider modelProvider){
        return new ItemArmorCustom(material, EntityEquipmentSlot.LEGS, null, modelProvider).setCreativeTab(this.creativeTab);
    }

    public Item createLeggings(ItemArmor.ArmorMaterial material, IUpdateCallback updateCallback){
        return new ItemArmorCustom(material, EntityEquipmentSlot.LEGS, updateCallback, null).setCreativeTab(this.creativeTab);
    }

    public Item createLeggings(ItemArmor.ArmorMaterial material, IUpdateCallback updateCallback, IArmorModelProvider modelProvider){
        return new ItemArmorCustom(material, EntityEquipmentSlot.LEGS, updateCallback, modelProvider).setCreativeTab(this.creativeTab);
    }

    public Item createBoots(ItemArmor.ArmorMaterial material){
        return new ItemArmorCustom(material, EntityEquipmentSlot.FEET, null, null).setCreativeTab(this.creativeTab);
    }

    public Item createBoots(ItemArmor.ArmorMaterial material, IArmorModelProvider modelProvider){
        return new ItemArmorCustom(material, EntityEquipmentSlot.FEET, null, modelProvider).setCreativeTab(this.creativeTab);
    }

    public Item createBoots(ItemArmor.ArmorMaterial material, IUpdateCallback updateCallback){
        return new ItemArmorCustom(material, EntityEquipmentSlot.FEET, updateCallback, null).setCreativeTab(this.creativeTab);
    }

    public Item createBoots(ItemArmor.ArmorMaterial material, IUpdateCallback updateCallback, IArmorModelProvider modelProvider){
        return new ItemArmorCustom(material, EntityEquipmentSlot.FEET, updateCallback, modelProvider).setCreativeTab(this.creativeTab);
    }

    private class ItemArmorCustom extends ItemArmor implements IContentRegister, IItemRenderTypeProvider, IIconItem {

        private String modid;
        private String itemName;
        private EntityEquipmentSlot slot;
        private IUpdateCallback updateCallback;
        private IArmorModelProvider modelProvider;
        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        public ItemArmorCustom(ArmorMaterial material, EntityEquipmentSlot slot, IUpdateCallback updateCallback, IArmorModelProvider modelProvider) {
            super(material, 0, slot);
            this.slot = slot;
            this.updateCallback = updateCallback;
            this.modelProvider = modelProvider;

            switch(slot){
                case HEAD:
                    this.itemName = "helmet_" + material.name().toLowerCase();
                    break;
                case CHEST:
                    this.itemName = "chestplate_" + material.name().toLowerCase();
                    break;
                case LEGS:
                    this.itemName = "leggings_" + material.name().toLowerCase();
                    break;
                case FEET:
                    this.itemName = "boots_" + material.name().toLowerCase();
                    break;
            }
        }

        @Override
        public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
            if(this.updateCallback != null){
                this.updateCallback.update(stack, world, player, 0, false);
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
            this.texture = register.registerIcon(this.modid + ":items/armor/" + this.itemName);
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
            return RenderItems.DEFAULT_ITEM;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack stack, EntityEquipmentSlot slot, ModelBiped defaultModel) {
            if(this.modelProvider != null){
                return this.modelProvider.getArmorModel(entity, stack, slot, defaultModel);
            }
            else{
                return super.getArmorModel(entity, stack, slot, defaultModel);
            }
        }

        public String getModid(){
            return this.modid;
        }

        public String getItemName(){
            return this.itemName;
        }

        public IArmorModelProvider getModelProvider(){
            return this.modelProvider;
        }

    }

}
