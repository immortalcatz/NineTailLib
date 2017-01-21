package keri.ninetaillib.util;

import keri.ninetaillib.block.BlockBase;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.item.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientUtils {

    public static void registerItemModel(Item item){
        if(!(item instanceof ItemBase)){
            throw new IllegalArgumentException("Item must be an instace of ItemBase !");
        }

        ItemBase itemBase = (ItemBase)item;

        if(itemBase.getSubNames() != null){
            ResourceLocation[] bakeryNames = new ResourceLocation[itemBase.getSubNames().length];

            for(int i = 0; i < itemBase.getSubNames().length; i++){
                bakeryNames[i] = new ResourceLocation(itemBase.getRegistryName().getResourceDomain(), itemBase.getInternalName() + "_" + itemBase.getSubNames()[i]);
            }

            ModelBakery.registerItemVariants(itemBase, bakeryNames);

            for(int i = 0; i < itemBase.getSubNames().length; i++){
                ResourceLocation rl = new ResourceLocation(itemBase.getRegistryName().getResourceDomain(), itemBase.getInternalName() + "_" + itemBase.getSubNames()[i]);
                ModelResourceLocation location = new ModelResourceLocation(rl, "inventory");
                ModelLoader.setCustomModelResourceLocation(itemBase, i, location);
            }
        }
        else{
            ResourceLocation rl = new ResourceLocation(itemBase.getRegistryName().getResourceDomain(), itemBase.getInternalName());
            ModelResourceLocation location = new ModelResourceLocation(rl, "inventory");
            ModelLoader.setCustomModelResourceLocation(itemBase, 0, location);
        }
    }

    public static void registerItemModel(Block block){
        if(!(block instanceof BlockBase)){
            throw new IllegalArgumentException("Block must be an instace of BlockBase !");
        }

        BlockBase blockBase = (BlockBase)block;

        if(blockBase instanceof IMetaBlock){
            IMetaBlock iface = (IMetaBlock)blockBase;
            ResourceLocation[] bakeryNames = new ResourceLocation[iface.getSubNames().length];

            for(int i = 0; i < iface.getSubNames().length; i++){
                bakeryNames[i] = new ResourceLocation(blockBase.getRegistryName().getResourceDomain(), blockBase.getInternalName() + "_" + iface.getSubNames()[i]);
            }

            ModelBakery.registerItemVariants(Item.getItemFromBlock(blockBase), bakeryNames);

            for(int i = 0; i < iface.getSubNames().length; i++){
                ResourceLocation rl = new ResourceLocation(blockBase.getRegistryName().getResourceDomain(), blockBase.getInternalName() + "_" + iface.getSubNames()[i]);
                ModelResourceLocation location = new ModelResourceLocation(rl, "inventory");
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockBase), i, location);
            }
        }
        else{
            ResourceLocation rl = new ResourceLocation(blockBase.getRegistryName().getResourceDomain(), blockBase.getInternalName());
            ModelResourceLocation location = new ModelResourceLocation(rl, "inventory");
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockBase), 0, location);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class SimpleStateMapper extends StateMapperBase {

        private ModelResourceLocation location;

        public SimpleStateMapper(ModelResourceLocation location){
            this.location = location;
        }

        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return this.location;
        }

    }

}
