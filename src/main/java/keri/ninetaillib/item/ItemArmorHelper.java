package keri.ninetaillib.item;

import keri.ninetaillib.render.player.IArmorModelProvider;
import keri.ninetaillib.render.registry.IRenderingRegistry;
import keri.ninetaillib.texture.IIconItem;
import keri.ninetaillib.texture.IIconRegistrar;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemArmorHelper {

    private String modid;

    public ItemArmorHelper(String modid){
        this.modid = modid;
    }

    public Item createArmorPiece(String materialName, ItemArmor.ArmorMaterial material, EntityEquipmentSlot type, IArmorModelProvider modelProvider){
        ItemArmorCustom item = new ItemArmorCustom(materialName, material, type, modelProvider, this.getRenderingRegistry());
        item.setCreativeTab(this.getCreativeTab());
        return item;
    }

    public Item createArmorPiece(String materialName, ItemArmor.ArmorMaterial material, EntityEquipmentSlot type){
        ItemArmorCustom item = new ItemArmorCustom(materialName, material, type, this.getRenderingRegistry());
        item.setCreativeTab(this.getCreativeTab());
        return item;
    }

    public Item[] createArmorSet(String materialName, ItemArmor.ArmorMaterial material, IArmorModelProvider modelProvider){
        Item[] items = new Item[4];
        items[0] = createArmorPiece(materialName, material, EntityEquipmentSlot.HEAD, modelProvider);
        items[1] = createArmorPiece(materialName, material, EntityEquipmentSlot.CHEST, modelProvider);
        items[2] = createArmorPiece(materialName, material, EntityEquipmentSlot.LEGS, modelProvider);
        items[3] = createArmorPiece(materialName, material, EntityEquipmentSlot.FEET, modelProvider);
        return items;
    }

    public Item[] createArmorSet(String materialName, ItemArmor.ArmorMaterial material){
        Item[] items = new Item[4];
        items[0] = createArmorPiece(materialName, material, EntityEquipmentSlot.HEAD);
        items[1] = createArmorPiece(materialName, material, EntityEquipmentSlot.CHEST);
        items[2] = createArmorPiece(materialName, material, EntityEquipmentSlot.LEGS);
        items[3] = createArmorPiece(materialName, material, EntityEquipmentSlot.FEET);
        return items;
    }

    public CreativeTabs getCreativeTab(){
        return CreativeTabs.COMBAT;
    }

    public abstract IRenderingRegistry getRenderingRegistry();

    private class ItemArmorCustom extends ItemArmor implements IIconItem {

        private IArmorModelProvider modelProvider;
        private String materialName;
        private String itemName;
        private IRenderingRegistry renderingRegistry;

        public ItemArmorCustom(String materialName, ArmorMaterial material, EntityEquipmentSlot slot, IRenderingRegistry renderingRegistry){
            super(material, 0, slot);
            this.materialName = materialName;
            this.modelProvider = null;
            this.renderingRegistry = renderingRegistry;
            this.register();
        }

        public ItemArmorCustom(String materialName, ArmorMaterial material, EntityEquipmentSlot slot, IArmorModelProvider modelProvider, IRenderingRegistry renderingRegistry){
            super(material, 0, slot);
            this.materialName = materialName;
            this.modelProvider = modelProvider;
            this.renderingRegistry = renderingRegistry;
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

            String itemName = namePrefix + this.materialName;
            this.itemName = itemName;
            this.setRegistryName(modid, itemName);
            this.setUnlocalizedName(modid + "." + itemName);

            if(this.renderingRegistry != null){
                this.renderingRegistry.handleItemSpecial(this);
            }

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

    }

}
