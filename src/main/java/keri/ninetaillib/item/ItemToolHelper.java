package keri.ninetaillib.item;

import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.texture.IIconItem;
import keri.ninetaillib.texture.IIconRegistrar;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemToolHelper {

    private String modid;

    public ItemToolHelper(String modid){
        this.modid = modid;
    }

    public Item createSword(Item.ToolMaterial material){
        return new ItemSwordCustom(material);
    }

    public Item createPickaxe(Item.ToolMaterial material){
        return new ItemPickaxeCustom(material);
    }

    public Item createShovel(Item.ToolMaterial material){
        return new ItemShovelCustom(material);
    }

    public Item createAxe(Item.ToolMaterial material){
        return new ItemAxeCustom(material);
    }

    public Item createHoe(Item.ToolMaterial material){
        return new ItemHoeCustom(material);
    }

    public CreativeTabs getCreativeTab(){
        return CreativeTabs.TOOLS;
    };

    private void register(String itemName, Item item){
        item.setRegistryName(this.modid, itemName);
        item.setUnlocalizedName(this.modid + "." + itemName);
        item.setCreativeTab(this.getCreativeTab());
        GameRegistry.register(item);
        NineTailLib.PROXY.handleItemSpecial(item);
    }

    private class ItemSwordCustom extends ItemSword implements IIconItem {

        public ItemSwordCustom(ToolMaterial material) {
            super(material);
            register("sword_" + material.name(), this);
        }

        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegistrar registrar) {
            this.texture = registrar.registerIcon(modid + ":items/tool/sword_" + this.getToolMaterialName().toLowerCase());
        }

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon(int meta) {
            return this.texture;
        }

    }

    private class ItemPickaxeCustom extends ItemPickaxe implements IIconItem {

        public ItemPickaxeCustom(ToolMaterial material) {
            super(material);
            register("pickaxe_" + material.name(), this);
        }

        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegistrar registrar) {
            this.texture = registrar.registerIcon(modid + ":items/tool/pickaxe_" + this.getToolMaterialName().toLowerCase());
        }

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon(int meta) {
            return this.texture;
        }

    }

    private class ItemShovelCustom extends ItemSpade implements IIconItem {

        public ItemShovelCustom(ToolMaterial material) {
            super(material);
            register("shovel_" + material.name(), this);
        }

        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegistrar registrar) {
            this.texture = registrar.registerIcon(modid + ":items/tool/shovel_" + this.getToolMaterialName().toLowerCase());
        }

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon(int meta) {
            return this.texture;
        }

    }

    private class ItemAxeCustom extends ItemAxe implements IIconItem {

        public ItemAxeCustom(ToolMaterial material) {
            super(material, material.getDamageVsEntity(), material.getEfficiencyOnProperMaterial());
            register("axe_" + material.name(), this);
        }

        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegistrar registrar) {
            this.texture = registrar.registerIcon(modid + ":items/tool/axe_" + this.getToolMaterialName().toLowerCase());
        }

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon(int meta) {
            return this.texture;
        }

    }

    private class ItemHoeCustom extends ItemHoe implements IIconItem {

        public ItemHoeCustom(ToolMaterial material) {
            super(material);
            register("hoe_" + material.name(), this);
        }

        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegistrar registrar) {
            this.texture = registrar.registerIcon(modid + ":items/tool/hoe_" + this.theToolMaterial.name().toLowerCase());
        }

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon(int meta) {
            return this.texture;
        }

    }

}
