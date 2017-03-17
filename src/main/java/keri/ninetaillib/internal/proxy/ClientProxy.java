package keri.ninetaillib.internal.proxy;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.packet.PacketCustom;
import com.google.common.collect.Lists;
import keri.ninetaillib.block.BlockBase;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.fluid.FluidBase;
import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.internal.client.ClientEventHandler;
import keri.ninetaillib.internal.client.gui.InventoryButtonHandler;
import keri.ninetaillib.internal.client.playereffect.PlayerEffectParticle;
import keri.ninetaillib.internal.client.playereffect.PlayerEffectSheep;
import keri.ninetaillib.internal.client.playereffect.PlayerEffectWolfs;
import keri.ninetaillib.internal.network.NineTailLibCPH;
import keri.ninetaillib.item.ItemBase;
import keri.ninetaillib.render.CustomBlockRenderer;
import keri.ninetaillib.render.CustomItemRenderer;
import keri.ninetaillib.render.IBlockRenderingHandler;
import keri.ninetaillib.render.IItemRenderingHandler;
import keri.ninetaillib.texture.IconRegistrar;
import keri.ninetaillib.util.ClientUtils;
import keri.ninetaillib.util.FluidStateMapper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.UUID;

public class ClientProxy implements INineTailProxy {

    private ArrayList<BlockBase> blocksToHandle = Lists.newArrayList();
    private ArrayList<ItemBase> itemsToHandle = Lists.newArrayList();
    private ArrayList<FluidBase> fluidsToHandle = Lists.newArrayList();

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(IconRegistrar.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new InventoryButtonHandler());
        MinecraftForge.EVENT_BUS.register(ClientEventHandler.INSTANCE);
        ClientEventHandler.INSTANCE.registerPlayerEffect(UUID.fromString("b2ac8c03-d994-4805-9e0f-57fede63c04d"), new PlayerEffectParticle(EnumParticleTypes.FLAME, -0.2D));
        ClientEventHandler.INSTANCE.registerPlayerEffect(UUID.fromString("9b2d08dc-720e-4e0b-bf30-c75b93239e25"), new PlayerEffectParticle(EnumParticleTypes.WATER_DROP, -0.1D));
        ClientEventHandler.INSTANCE.registerPlayerEffect(UUID.fromString("1e25868f-6372-492d-8319-3a4627f0cc18"), new PlayerEffectParticle(EnumParticleTypes.DRAGON_BREATH, -0.2D));
        ClientEventHandler.INSTANCE.registerPlayerEffect(UUID.fromString("0da699a0-6486-4c9a-916e-7144e39dc8b7"), new PlayerEffectParticle(EnumParticleTypes.SLIME, -0.2D));
        ClientEventHandler.INSTANCE.registerPlayerEffect(UUID.fromString("58d506e2-7ee7-4774-8b22-c7a57eda488b"), new PlayerEffectSheep());
        ClientEventHandler.INSTANCE.registerPlayerEffect(UUID.fromString("1e2326e7-a592-4e11-9b4c-d0c930deeca3"), new PlayerEffectWolfs());

        for(BlockBase block : this.blocksToHandle){
            IconRegistrar.INSTANCE.registerBlock(block);
            this.registerRenderingHandler(block, block.getRenderingHandler());

            if(NineTailLib.CONFIG.enableBlockModelDebug){
                NineTailLib.LOGGER.debug(block.getUnlocalizedName());
            }
        }

        if(NineTailLib.CONFIG.enableBlockModelDebug){
            NineTailLib.LOGGER.debug("Successfully loaded " + this.blocksToHandle.size() + " block models !");
        }

        for(ItemBase item : this.itemsToHandle){
            IconRegistrar.INSTANCE.registerItem(item);
            this.registerRenderingHandler(item, item.getRenderingHandler());

            if(NineTailLib.CONFIG.enableItemModelDebug){
                NineTailLib.LOGGER.debug(item.getUnlocalizedName());
            }
        }

        if(NineTailLib.CONFIG.enableItemModelDebug){
            NineTailLib.LOGGER.debug("Successfully loaded " + this.itemsToHandle.size() + " item models !");
        }

        for(FluidBase fluid : this.fluidsToHandle) {
            this.registerFluidModel(fluid);
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        PacketCustom.assignHandler(NineTailLib.INSTANCE, new NineTailLibCPH());
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

    private void registerFluidModel(FluidBase fluid){
        Block block = fluid.getBlock();
        Item item = Item.getItemFromBlock(block);
        FluidStateMapper mapper = new FluidStateMapper(fluid);
        ModelLoader.registerItemVariants(item);
        ModelLoader.setCustomStateMapper(block, mapper);
        ModelLoader.setCustomMeshDefinition(item, mapper);
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
                ModelLoader.setCustomStateMapper(blockBase, new ClientUtils.SimpleStateMapper(location));
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
            ModelLoader.setCustomStateMapper(blockBase, new ClientUtils.SimpleStateMapper(location));
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

}
