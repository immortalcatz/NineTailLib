package keri.ninetaillib.item;

import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.render.IArmorModelProvider;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

        private IArmorModelProvider modelProvider = null;

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

        @SideOnly(Side.CLIENT)
        public void setModelProvider(IArmorModelProvider provider){
            this.modelProvider = provider;
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

        @Override
        @SideOnly(Side.CLIENT)
        public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type){
            return null;
        }

    }

}
