package keri.ninetaillib.internal.proxy;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.packet.PacketCustom;
import codechicken.lib.render.CCModelState;
import codechicken.lib.util.TransformUtils;
import com.google.common.collect.Lists;
import keri.ninetaillib.block.BlockBase;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.fluid.FluidBase;
import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.internal.client.gui.InventoryButtonHandler;
import keri.ninetaillib.internal.client.handler.ClientEventHandler;
import keri.ninetaillib.internal.client.handler.PlayerRenderHandler;
import keri.ninetaillib.internal.network.NineTailLibCPH;
import keri.ninetaillib.item.ItemBase;
import keri.ninetaillib.render.*;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.Map;

public class ClientProxy implements INineTailProxy {

    private ArrayList<BlockBase> blocksToHandle = Lists.newArrayList();
    private ArrayList<ItemBase> itemsToHandle = Lists.newArrayList();
    private ArrayList<Item> specialItemToHandle = Lists.newArrayList();
    private ArrayList<FluidBase> fluidsToHandle = Lists.newArrayList();

    @Override
    public void preInit(FMLPreInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(IconRegistrar.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new InventoryButtonHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        this.blocksToHandle.forEach(block -> this.registerBlock(block));
        this.itemsToHandle.forEach(item -> this.registerItem(item));
        this.fluidsToHandle.forEach(fluid -> this.registerFluidModel(fluid));
        this.specialItemToHandle.forEach(item -> this.registerSpecialItemModel(item));

        if(NineTailLib.CONFIG.enableBlockModelDebug){
            NineTailLib.LOGGER.debug("Successfully loaded " + this.blocksToHandle.size() + " block models !");
        }

        if(NineTailLib.CONFIG.enableItemModelDebug){
            NineTailLib.LOGGER.debug("Successfully loaded " + this.itemsToHandle.size() + " item models !");
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        PacketCustom.assignHandler(NineTailLib.INSTANCE, new NineTailLibCPH());
        this.registerAuxRenderers();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void handleBlock(BlockBase block) {
        this.blocksToHandle.add(block);
    }

    @Override
    public void handleItem(ItemBase item) {
        this.itemsToHandle.add(item);
    }

    @Override
    public void handleFluid(FluidBase fluid) {
        this.fluidsToHandle.add(fluid);
    }

    @Override
    public void handleItemSpecial(Item item) {
        this.specialItemToHandle.add(item);
    }

    private void registerAuxRenderers(){
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        RenderPlayer renderer = null;
        renderer = skinMap.get("default");
        renderer.addLayer(new PlayerRenderHandler());
        renderer = skinMap.get("slim");
        renderer.addLayer(new PlayerRenderHandler());
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
