package keri.ninetaillib.item;

import keri.ninetaillib.internal.NineTailLib;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemArmorHelper {

    private String modid;

    public ItemArmorHelper(String modid){
        this.modid = modid;
    }

    public Item createArmorPiece(ItemArmor.ArmorMaterial material, EntityEquipmentSlot type){
        //todo implement item creation
        return null;
    }

    public CreativeTabs getCreativeTab(){
        return CreativeTabs.COMBAT;
    }

    private class ItemArmorCustom extends ItemArmor {

        public ItemArmorCustom(ArmorMaterial material, EntityEquipmentSlot slot){
            super(material, 0, slot);
            this.register();
        }

        private void register(){
            String namePrefix = null;

            switch(this.getEquipmentSlot()){
                case HEAD: namePrefix = "helmet_";
                case CHEST: namePrefix = "chestplate_";
                case LEGS: namePrefix = "leggings_";
                case FEET: namePrefix = "boots_";
            }

            String itemName = namePrefix + this.getArmorMaterial().getName();
            this.setRegistryName(modid, itemName);
            this.setUnlocalizedName(modid + "." + itemName);
            this.setCreativeTab(getCreativeTab());
            NineTailLib.PROXY.handleItemSpecial(this);
            GameRegistry.register(this);
        }

        //todo implement model provider and textures !

    }

}
