package keri.ninetaillib.item;

import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.render.IArmorModelProvider;
import keri.ninetaillib.texture.IIconItem;
import keri.ninetaillib.texture.IIconRegistrar;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorHelper {

    private String modid;

    public ItemArmorHelper(String modid){
        this.modid = modid;
    }

    public Item createArmorPiece(ItemArmor.ArmorMaterial material, EntityEquipmentSlot type, IArmorModelProvider modelProvider){
        ItemArmorCustom item = new ItemArmorCustom(material, type, modelProvider);
        item.setCreativeTab(this.getCreativeTab());
        return item;
    }

    public Item createArmorPiece(ItemArmor.ArmorMaterial material, EntityEquipmentSlot type){
        ItemArmorCustom item = new ItemArmorCustom(material, type);
        item.setCreativeTab(this.getCreativeTab());
        return item;
    }

    public Item[] createArmorSet(ItemArmor.ArmorMaterial material, IArmorModelProvider modelProvider){
        Item[] items = new Item[4];
        items[0] = createArmorPiece(material, EntityEquipmentSlot.HEAD, modelProvider);
        items[1] = createArmorPiece(material, EntityEquipmentSlot.CHEST, modelProvider);
        items[2] = createArmorPiece(material, EntityEquipmentSlot.LEGS, modelProvider);
        items[3] = createArmorPiece(material, EntityEquipmentSlot.FEET, modelProvider);
        return items;
    }

    public Item[] createArmorSet(ItemArmor.ArmorMaterial material){
        Item[] items = new Item[4];
        items[0] = createArmorPiece(material, EntityEquipmentSlot.HEAD);
        items[1] = createArmorPiece(material, EntityEquipmentSlot.CHEST);
        items[2] = createArmorPiece(material, EntityEquipmentSlot.LEGS);
        items[3] = createArmorPiece(material, EntityEquipmentSlot.FEET);
        return items;
    }

    public CreativeTabs getCreativeTab(){
        return CreativeTabs.COMBAT;
    }

    private class ItemArmorCustom extends ItemArmor implements IIconItem {

        private IArmorModelProvider modelProvider;
        private String itemName;

        public ItemArmorCustom(ArmorMaterial material, EntityEquipmentSlot slot){
            super(material, 0, slot);
            this.modelProvider = null;
            this.register();
        }

        public ItemArmorCustom(ArmorMaterial material, EntityEquipmentSlot slot, IArmorModelProvider modelProvider){
            super(material, 0, slot);
            this.modelProvider = modelProvider;
            this.register();
        }

        private void register(){
            String namePrefix = null;

            switch(this.getEquipmentSlot()){
                case HEAD:
                    namePrefix = "helmet_";
                    break;
                case CHEST:
                    namePrefix = "chestplate_";
                    break;
                case LEGS:
                    namePrefix = "leggings_";
                    break;
                case FEET:
                    namePrefix = "boots_";
                    break;
            }

            String itemName = namePrefix + this.getArmorMaterial().getName().toLowerCase();
            this.itemName = itemName;
            this.setRegistryName(modid, itemName);
            this.setUnlocalizedName(modid + "." + itemName);
            NineTailLib.PROXY.handleItemSpecial(this);
            GameRegistry.register(this);
        }

        @SideOnly(Side.CLIENT)
        private TextureAtlasSprite texture;

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegistrar registrar) {
            this.texture = registrar.registerIcon(modid + ":items/armor/" + this.itemName);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public TextureAtlasSprite getIcon(int meta) {
            return this.texture;
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
            ResourceLocation texture1 = new ResourceLocation(modid, "textures/models/armor/" + this.itemName + "_1.png");
            ResourceLocation texture2 = new ResourceLocation(modid, "textures/models/armor/" + this.itemName + "_2.png");
            return this.armorType.getIndex() == 2 ? texture2.toString() : texture1.toString();
        }

    }

}
