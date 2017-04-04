package keri.ninetaillib.item;

import keri.ninetaillib.internal.NineTailLib;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

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

    private class ItemSwordCustom extends ItemSword {

        public ItemSwordCustom(ToolMaterial material) {
            super(material);
            register("sword_" + material.name(), this);
        }

    }

    private class ItemPickaxeCustom extends ItemPickaxe {

        public ItemPickaxeCustom(ToolMaterial material) {
            super(material);
            register("pickaxe_" + material.name(), this);
        }

    }

    private class ItemShovelCustom extends ItemSpade {

        public ItemShovelCustom(ToolMaterial material) {
            super(material);
            register("shovel_" + material.name(), this);
        }

    }

    private class ItemAxeCustom extends ItemAxe {

        public ItemAxeCustom(ToolMaterial material) {
            super(material);
            register("axe_" + material.name(), this);
        }

    }

    private class ItemHoeCustom extends ItemHoe {

        public ItemHoeCustom(ToolMaterial material) {
            super(material);
            register("hoe_" + material.name(), this);
        }

    }

}
