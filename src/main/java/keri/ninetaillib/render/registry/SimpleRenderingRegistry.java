package keri.ninetaillib.render.registry;

import baubles.api.IBauble;
import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.render.CCModelState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.util.TransformUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import keri.ninetaillib.block.BlockBase;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.fluid.FluidBase;
import keri.ninetaillib.internal.proxy.ClientProxy;
import keri.ninetaillib.item.ItemBase;
import keri.ninetaillib.render.player.PlayerRenderHandler;
import keri.ninetaillib.render.render.CustomBlockRenderer;
import keri.ninetaillib.render.render.CustomItemRenderer;
import keri.ninetaillib.render.render.DefaultItemRenderer;
import keri.ninetaillib.texture.IIconItem;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.ninetaillib.util.FluidStateMapper;
import keri.ninetaillib.util.MLESupressor;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public abstract class SimpleRenderingRegistry implements IRenderingRegistry {

    private Map<String, List<BlockBase>> blocksToHandle = Maps.newHashMap();
    private Map<String, List<ItemBase>> itemsToHandle = Maps.newHashMap();
    private Map<String, List<FluidBase>> fluidsToHandle = Maps.newHashMap();
    private Map<String, List<Item>> specialItemToHandle = Maps.newHashMap();
    private Map<String, List<IBaubleRenderingHandler>> baubleRenderingHandlers = Maps.newHashMap();

    public void preInit(){
        if(this.blocksToHandle.containsKey(this.getModid())){
            this.blocksToHandle.get(this.getModid()).forEach(block -> this.registerBlock(block));
        }

        if(this.itemsToHandle.containsKey(this.getModid())){
            this.itemsToHandle.get(this.getModid()).forEach(item -> this.registerItem(item));
        }

        if(this.fluidsToHandle.containsKey(this.getModid())){
            this.fluidsToHandle.get(this.getModid()).forEach(fluid -> this.registerFluidModel(fluid));
        }

        if(this.specialItemToHandle.containsKey(this.getModid())){
            this.specialItemToHandle.get(this.getModid()).forEach(item -> this.registerSpecialItemModel(item));
        }
    }

    public void init(){
        if(this.baubleRenderingHandlers.containsKey(this.getModid())){
            Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
            RenderPlayer renderer = null;
            renderer = skinMap.get("default");
            renderer.addLayer(new PlayerRenderHandler(this.baubleRenderingHandlers.get(this.getModid())));
            renderer = skinMap.get("slim");
            renderer.addLayer(new PlayerRenderHandler(this.baubleRenderingHandlers.get(this.getModid())));
        }
    }

    public void postInit(){
        if(this.blocksToHandle.containsKey(this.getModid())){
            this.blocksToHandle.get(this.getModid()).forEach(block -> this.registerBlockPost(block));
        }
    }

    @Override
    public void handleBlock(BlockBase block){
        if(block != null){
            if(this.blocksToHandle.containsKey(this.getModid())){
                this.blocksToHandle.get(this.getModid()).add(block);
            }
            else{
                List<BlockBase> list = Lists.newArrayList();
                list.add(block);
                this.blocksToHandle.put(this.getModid(), list);
            }
        }
        else{
            throw new IllegalArgumentException("Block cannot be null !");
        }
    }

    @Override
    public void handleItem(ItemBase item){
        if(item != null){
            if(this.itemsToHandle.containsKey(this.getModid())){
                this.itemsToHandle.get(this.getModid()).add(item);
            }
            else{
                List<ItemBase> list = Lists.newArrayList();
                list.add(item);
                this.itemsToHandle.put(this.getModid(), list);
            }

            if(item.getBaubleRenderingHandler() != null && item instanceof IBauble){
                if(this.baubleRenderingHandlers.containsKey(this.getModid())){
                    this.baubleRenderingHandlers.get(this.getModid()).add(item.getBaubleRenderingHandler());
                }
                else{
                    List<IBaubleRenderingHandler> list = Lists.newArrayList();
                    list.add(item.getBaubleRenderingHandler());
                    this.baubleRenderingHandlers.put(this.getModid(), list);
                }
            }
        }
        else{
            throw new IllegalArgumentException("Item cannot be null !");
        }
    }

    @Override
    public void handleFluid(FluidBase fluid){
        if(fluid != null){
            if(this.fluidsToHandle.containsKey(this.getModid())){
                this.fluidsToHandle.get(this.getModid()).add(fluid);
            }
            else{
                List<FluidBase> list = Lists.newArrayList();
                list.add(fluid);
                this.fluidsToHandle.put(this.getModid(), list);
            }
        }
        else{
            throw new IllegalArgumentException("Fluid cannot be null !");
        }
    }

    @Override
    public void handleItemSpecial(Item item){
        if(item != null){
            if(this.specialItemToHandle.containsKey(this.getModid())){
                this.specialItemToHandle.get(this.getModid()).add(item);
            }
            else{
                List<Item> list = Lists.newArrayList();
                list.add(item);
                this.specialItemToHandle.put(this.getModid(), list);
            }
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

        new MLESupressor(block).supress(false, block instanceof IMetaBlock);
        ModelResourceLocation locationInventory = new ModelResourceLocation(block.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, locationInventory);
        ModelRegistryHelper.register(locationInventory, new CustomItemRenderer(renderer));
    }

    private void registerRenderingHandlerPost(Block block, IBlockRenderingHandler renderer){
        if(block == null || renderer == null){
            throw new IllegalArgumentException("Block or rendering handler can't be null !");
        }
        if(!(block instanceof BlockBase)){
            throw new IllegalArgumentException("Block must be an instance of BlockBase !");
        }

        BlockRenderingRegistry.registerRenderer(ClientProxy.renderType, new CustomBlockRenderer(renderer));
    }

    private void registerRenderingHandler(Item item, IItemRenderingHandler renderer){
        if(item == null || renderer == null){
            throw new IllegalArgumentException("Item or rendering handler can't be null !");
        }
        if(!(item instanceof ItemBase)){
            throw new IllegalArgumentException("Item must be an instance of ItemBase !");
        }

        ModelResourceLocation location = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, location);
        ModelRegistryHelper.register(location, new CustomItemRenderer(renderer));
    }

    private void registerBlock(BlockBase block){
        this.getIconRegistrar().registerBlock(block);
        this.registerRenderingHandler(block, block.getRenderingHandler());
    }

    private void registerBlockPost(BlockBase block){
        this.registerRenderingHandlerPost(block, block.getRenderingHandler());
    }

    private void registerItem(ItemBase item){
        this.getIconRegistrar().registerItem(item);
        this.registerRenderingHandler(item, item.getRenderingHandler());
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
        this.getIconRegistrar().registerItem((IIconItem)item);
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

        ModelResourceLocation location = new ModelResourceLocation(rl, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, location);
        ModelRegistryHelper.registerItemRenderer(item, new CustomItemRenderer(new DefaultItemRenderer(itemTransforms)));
    }

    public abstract String getModid();

    public abstract IIconRegistrar getIconRegistrar();

}
