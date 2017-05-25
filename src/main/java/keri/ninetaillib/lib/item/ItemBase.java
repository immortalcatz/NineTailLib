/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.item;

import keri.ninetaillib.lib.util.IContentRegister;
import keri.ninetaillib.lib.util.ILocalization;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase extends Item implements IContentRegister {

    private String itemName;
    private String[] subNames;

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
        if(this instanceof ILocalization){
            return ((ILocalization)this).getUnlocalizedName(stack);
        }
        else{
            if(this.subNames != null){
                return this.getUnlocalizedName() + "." + this.subNames[stack.getMetadata()];
            }
            else{
                return super.getUnlocalizedName(stack);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab(){
        return CreativeTabs.MISC;
    }

    @Override
    public void handlePreInit(FMLPreInitializationEvent event) {
        String modid = event.getModMetadata().modId;
        this.setRegistryName(modid, this.itemName);
        this.setUnlocalizedName(modid + "." + this.itemName);
        GameRegistry.register(this);
    }

    @Override
    public void handleInit(FMLInitializationEvent event) {

    }

    @Override
    public void handlePostInit(FMLPostInitializationEvent event) {

    }

}
