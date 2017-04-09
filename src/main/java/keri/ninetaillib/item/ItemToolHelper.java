package keri.ninetaillib.item;

import keri.ninetaillib.render.registry.IRenderingRegistry;
import keri.ninetaillib.texture.IIconItem;
import keri.ninetaillib.texture.IIconRegistrar;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemToolHelper {

    private String modid;

    public ItemToolHelper(String modid){
        this.modid = modid;
    }

    public Item createSword(String materialName, Item.ToolMaterial material){
        return new ItemSwordCustom(materialName, material);
    }

    public Item createPickaxe(String materialName, Item.ToolMaterial material){
        return new ItemPickaxeCustom(materialName, material);
    }

    public Item createShovel(String materialName, Item.ToolMaterial material){
        return new ItemShovelCustom(materialName, material);
    }

    public Item createAxe(String materialName, Item.ToolMaterial material){
        return new ItemAxeCustom(materialName, material);
    }

    public Item createHoe(String materialName, Item.ToolMaterial material){
        return new ItemHoeCustom(materialName, material);
    }

    public Item[] createToolset(String materialName, Item.ToolMaterial material){
        Item[] items = new Item[5];
        items[0] = createSword(materialName, material);
        items[1] = createPickaxe(materialName, material);
        items[2] = createShovel(materialName, material);
        items[3] = createAxe(materialName, material);
        items[4] = createHoe(materialName, material);
        return items;
    }

    public CreativeTabs getCreativeTab(){
        return CreativeTabs.TOOLS;
    };

    public abstract IRenderingRegistry getRenderingRegistry();

    private void register(String itemName, Item item){
        item.setRegistryName(this.modid, itemName);
        item.setUnlocalizedName(this.modid + "." + itemName);
        item.setCreativeTab(this.getCreativeTab());
        GameRegistry.register(item);
        this.getRenderingRegistry().handleItemSpecial(item);
    }

    private class ItemSwordCustom extends ItemSword implements IIconItem {

        private String materialName;

        public ItemSwordCustom(String materialName, ToolMaterial material) {
            super(material);
            this.materialName = materialName;
            register("sword_" + materialName, this);
        }

        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegistrar registrar) {
            this.texture = registrar.registerIcon(modid + ":items/tool/sword_" + this.materialName);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon(int meta) {
            return this.texture;
        }

    }

    private class ItemPickaxeCustom extends ItemPickaxe implements IIconItem {

        private String materialName;

        public ItemPickaxeCustom(String materialName, ToolMaterial material) {
            super(material);
            this.materialName = materialName;
            register("pickaxe_" + materialName, this);
        }

        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegistrar registrar) {
            this.texture = registrar.registerIcon(modid + ":items/tool/pickaxe_" + this.materialName);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon(int meta) {
            return this.texture;
        }

    }

    private class ItemShovelCustom extends ItemSpade implements IIconItem {

        private String materialName;

        public ItemShovelCustom(String materialName, ToolMaterial material) {
            super(material);
            this.materialName = materialName;
            register("shovel_" + materialName, this);
        }

        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegistrar registrar) {
            this.texture = registrar.registerIcon(modid + ":items/tool/shovel_" + this.materialName);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon(int meta) {
            return this.texture;
        }

    }

    private class ItemAxeCustom extends ItemAxe implements IIconItem {

        private String materialName;

        public ItemAxeCustom(String materialName, ToolMaterial material) {
            super(material, material.getDamageVsEntity(), material.getEfficiencyOnProperMaterial());
            this.materialName = materialName;
            register("axe_" + materialName, this);
        }

        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegistrar registrar) {
            this.texture = registrar.registerIcon(modid + ":items/tool/axe_" + this.materialName);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon(int meta) {
            return this.texture;
        }

    }

    private class ItemHoeCustom extends ItemHoe implements IIconItem {

        private String materialName;

        public ItemHoeCustom(String materialName, ToolMaterial material) {
            super(material);
            this.materialName = materialName;
            register("hoe_" + materialName, this);
        }

        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegistrar registrar) {
            this.texture = registrar.registerIcon(modid + ":items/tool/hoe_" + this.materialName);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon(int meta) {
            return this.texture;
        }

    }

}
