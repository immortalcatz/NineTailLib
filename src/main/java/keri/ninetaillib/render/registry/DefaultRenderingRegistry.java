package keri.ninetaillib.render.registry;

import baubles.api.IBauble;
import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.render.CCModelState;
import codechicken.lib.util.TransformUtils;
import com.google.common.collect.Lists;
import keri.ninetaillib.block.BlockBase;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.fluid.FluidBase;
import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.item.ItemBase;
import keri.ninetaillib.render.block.CustomBlockRenderer;
import keri.ninetaillib.render.block.IBlockRenderingHandler;
import keri.ninetaillib.render.item.CustomItemRenderer;
import keri.ninetaillib.render.item.DefaultItemRenderer;
import keri.ninetaillib.render.item.IBaubleRenderingHandler;
import keri.ninetaillib.render.item.IItemRenderingHandler;
import keri.ninetaillib.render.player.PlayerRenderHandler;
import keri.ninetaillib.texture.IIconItem;
import keri.ninetaillib.texture.IconRegistrar;
import keri.ninetaillib.util.FluidStateMapper;
import keri.ninetaillib.util.SimpleStateMapper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class DefaultRenderingRegistry implements IRenderingRegistry {

    private ArrayList<BlockBase> blocksToHandle = Lists.newArrayList();
    private ArrayList<ItemBase> itemsToHandle = Lists.newArrayList();
    private ArrayList<FluidBase> fluidsToHandle = Lists.newArrayList();
    private ArrayList<Item> specialItemToHandle = Lists.newArrayList();
    private ArrayList<IBaubleRenderingHandler> baubleRenderingHandlers = Lists.newArrayList();

    public void preInit(){
        this.blocksToHandle.forEach(block -> this.registerBlock(block));
        this.itemsToHandle.forEach(item -> this.registerItem(item));
        this.fluidsToHandle.forEach(fluid -> this.registerFluidModel(fluid));
        this.specialItemToHandle.forEach(item -> this.registerSpecialItemModel(item));
    }

    public void init(){
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        RenderPlayer renderer = null;
        renderer = skinMap.get("default");
        renderer.addLayer(new PlayerRenderHandler(this.baubleRenderingHandlers));
        renderer = skinMap.get("slim");
        renderer.addLayer(new PlayerRenderHandler(this.baubleRenderingHandlers));
    }

    @Override
    public void handleBlock(BlockBase block) {
        if(block != null){
            this.blocksToHandle.add(block);
        }
        else{
            throw new IllegalArgumentException("Block cannot be null !");
        }
    }

    @Override
    public void handleItem(ItemBase item) {
        if(item != null){
            this.itemsToHandle.add(item);

            if(item.getBaubleRenderingHandler() != null && item instanceof IBauble){
                this.baubleRenderingHandlers.add(item.getBaubleRenderingHandler());
            }
        }
        else{
            throw new IllegalArgumentException("Item cannot be null !");
        }
    }

    @Override
    public void handleFluid(FluidBase fluid) {
        if(fluid != null){
            this.fluidsToHandle.add(fluid);
        }
        else{
            throw new IllegalArgumentException("Fluid cannot be null !");
        }
    }

    @Override
    public void handleItemSpecial(Item item) {
        if(item != null){
            this.specialItemToHandle.add(item);
        }
        else{
            throw new IllegalArgumentException("Item cannot be null !");
        }
    }

    private void registerRenderingHandler(Block block, IBlockRenderingHandler renderer){
        if(block == null || renderer == null){
            throw new IllegalArgumentException("Block or rendering handler can't be null !");
        }
        if(!(block instanceof BlockBase)){
            throw new IllegalArgumentException("Block must be an instance of BlockBase !");
        }

        BlockBase blockBase = (BlockBase)block;
        ResourceLocation rl = new ResourceLocation(blockBase.getRegistryName().getResourceDomain(), blockBase.getInternalName());

        if(blockBase instanceof IMetaBlock){
            IMetaBlock iface = (IMetaBlock)blockBase;
            CustomBlockRenderer blockRenderer = new CustomBlockRenderer(renderer);
            CustomItemRenderer itemRenderer = new CustomItemRenderer(renderer);
            ModelResourceLocation locationInventory = new ModelResourceLocation(rl, "inventory");

            for(int i = 0; i < iface.getSubNames().length; i++){
                ModelResourceLocation location = new ModelResourceLocation(rl, "type=" + iface.getSubNames()[i]);
                ModelLoader.setCustomStateMapper(blockBase, new SimpleStateMapper(location));
                ModelRegistryHelper.register(location, blockRenderer);
            }

            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockBase), 0, locationInventory);
            ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(blockBase), itemRenderer);
        }
        else{
            CustomBlockRenderer blockRenderer = new CustomBlockRenderer(renderer);
            CustomItemRenderer itemRenderer = new CustomItemRenderer(renderer);
            ModelResourceLocation location = new ModelResourceLocation(rl, "normal");
            ModelResourceLocation locationInventory = new ModelResourceLocation(rl, "inventory");
            ModelLoader.setCustomStateMapper(blockBase, new SimpleStateMapper(location));
            ModelRegistryHelper.register(location, blockRenderer);
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockBase), 0, locationInventory);
            ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(blockBase), itemRenderer);
        }
    }

    private void registerRenderingHandler(Item item, IItemRenderingHandler renderer){
        if(item == null || renderer == null){
            throw new IllegalArgumentException("Item or rendering handler can't be null !");
        }
        if(!(item instanceof ItemBase)){
            throw new IllegalArgumentException("Item must be an instance of ItemBase !");
        }

        ItemBase itemBase = (ItemBase)item;
        ResourceLocation rl = new ResourceLocation(itemBase.getRegistryName().getResourceDomain(), itemBase.getInternalName());

        if(itemBase.getSubNames() != null){
            CustomItemRenderer itemRenderer = new CustomItemRenderer(renderer);
            ModelResourceLocation location = new ModelResourceLocation(rl, "inventory");

            for(int i = 0; i < itemBase.getSubNames().length; i++){
                ModelLoader.setCustomModelResourceLocation(itemBase, 0, location);
            }

            ModelRegistryHelper.registerItemRenderer(itemBase, itemRenderer);
        }
        else{
            CustomItemRenderer itemRenderer = new CustomItemRenderer(renderer);
            ModelResourceLocation location = new ModelResourceLocation(rl, "inventory");
            ModelLoader.setCustomModelResourceLocation(itemBase, 0, location);
            ModelRegistryHelper.registerItemRenderer(itemBase, itemRenderer);
        }
    }

    private void registerBlock(BlockBase block){
        IconRegistrar.INSTANCE.registerBlock(block);
        this.registerRenderingHandler(block, block.getRenderingHandler());

        if(NineTailLib.CONFIG.enableBlockModelDebug){
            NineTailLib.LOGGER.debug(block.getUnlocalizedName());
        }
    }

    private void registerItem(ItemBase item){
        IconRegistrar.INSTANCE.registerItem(item);
        this.registerRenderingHandler(item, item.getRenderingHandler());

        if(NineTailLib.CONFIG.enableItemModelDebug){
            NineTailLib.LOGGER.debug(item.getUnlocalizedName());
        }
    }

    private void registerFluidModel(FluidBase fluid){
        Block block = fluid.getBlock();
        Item item = Item.getItemFromBlock(block);
        FluidStateMapper mapper = new FluidStateMapper(fluid);
        ModelLoader.registerItemVariants(item);
        ModelLoader.setCustomStateMapper(block, mapper);
        ModelLoader.setCustomMeshDefinition(item, mapper);
    }

    private void registerSpecialItemModel(Item item){
        IconRegistrar.INSTANCE.registerItem((IIconItem)item);
        ResourceLocation rl = item.getRegistryName();
        CCModelState itemTransforms;

        if(item instanceof ItemSword || item instanceof ItemPickaxe || item instanceof ItemSpade || item instanceof ItemAxe || item instanceof ItemHoe){
            itemTransforms = TransformUtils.DEFAULT_TOOL;
        }
        else if(item instanceof ItemBow){
            itemTransforms = TransformUtils.DEFAULT_BOW;
        }
        else if(item instanceof ItemFishingRod){
            itemTransforms = TransformUtils.DEFAULT_HANDHELD_ROD;
        }
        else{
            itemTransforms = TransformUtils.DEFAULT_ITEM;
        }

        CustomItemRenderer itemRenderer = new CustomItemRenderer(new DefaultItemRenderer(itemTransforms));
        ModelResourceLocation location = new ModelResourceLocation(rl, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, location);
        ModelRegistryHelper.registerItemRenderer(item, itemRenderer);
    }

}
