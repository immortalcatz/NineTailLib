/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.block;

import codechicken.lib.render.particle.CustomParticleHandler;
import codechicken.lib.texture.IWorldBlockTextureProvider;
import codechicken.lib.texture.TextureUtils;
import keri.ninetaillib.lib.item.ItemBlockBase;
import keri.ninetaillib.lib.mod.IContentRegister;
import keri.ninetaillib.lib.property.CommonProperties;
import keri.ninetaillib.lib.property.PropertyDataHolder;
import keri.ninetaillib.lib.render.RenderBlocks;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.texture.IIconBlock;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockBase<T extends TileEntity> extends Block implements ITileEntityProvider, IContentRegister, IIconBlock, IWorldBlockTextureProvider {

    private String modid;
    private String blockName;
    private String[] subNames = null;
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockBase(String blockName, Material material, MapColor mapColor) {
        super(material, mapColor);
        this.blockName = blockName;
        this.setCreativeTab(this.getCreativeTab());
        this.setSoundType(this.getSoundType(material));
        this.setDefaultState(this.blockState.getBaseState().withProperty(CommonProperties.META_DATA, 0));
    }

    public BlockBase(String blockName, Material material) {
        super(material);
        this.blockName = blockName;
        this.setCreativeTab(this.getCreativeTab());
        this.setSoundType(this.getSoundType(material));
        this.setDefaultState(this.blockState.getBaseState().withProperty(CommonProperties.META_DATA, 0));
    }

    public BlockBase(String blockName, Material material, MapColor mapColor, String... subNames) {
        super(material, mapColor);
        this.blockName = blockName;
        this.subNames = subNames;
        this.setCreativeTab(this.getCreativeTab());
        this.setSoundType(this.getSoundType(material));
        this.setDefaultState(this.blockState.getBaseState().withProperty(CommonProperties.META_DATA, 0));
    }

    public BlockBase(String blockName, Material material, String... subNames){
        super(material);
        this.blockName = blockName;
        this.subNames = subNames;
        this.setCreativeTab(this.getCreativeTab());
        this.setSoundType(this.getSoundType(material));
        this.setDefaultState(this.blockState.getBaseState().withProperty(CommonProperties.META_DATA, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{CommonProperties.META_DATA}, new IUnlistedProperty[]{CommonProperties.DATA_HOLDER_PROPERTY});
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        PropertyDataHolder dataHolder = new PropertyDataHolder();
        dataHolder = this.initializePropertyData(dataHolder, world, pos);
        return ((IExtendedBlockState)state).withProperty(CommonProperties.DATA_HOLDER_PROPERTY, dataHolder);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(CommonProperties.META_DATA);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(CommonProperties.META_DATA, meta);
    }

    @Nullable
    @Override
    public T createNewTileEntity(World world, int meta) {
        return null;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.subNames != null ? BlockAccessUtils.getBlockMetadata(state) : super.damageDropped(state);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        if(this.subNames != null){
            return new ItemStack(this, 1, BlockAccessUtils.getBlockMetadata(world, pos));
        }
        else{
            return new ItemStack(this, 1, 0);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
        if(this.subNames != null){
            for(int i = 0; i < this.subNames.length; i++){
                list.add(new ItemStack(this, 1, i));
            }
        }
        else{
            list.add(new ItemStack(this, 1, 0));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register){
        if(this.subNames != null){
            this.texture = new TextureAtlasSprite[this.subNames.length];

            for(int i = 0; i < this.subNames.length; i++){
                this.texture[i] = register.registerIcon(this.modid + ":blocks/" + this.blockName + "_" + this.subNames[i]);
            }
        }
        else{
            this.texture = new TextureAtlasSprite[1];
            this.texture[0] = register.registerIcon(this.modid + ":blocks/" + this.blockName);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, EnumFacing side) {
        if(this.getRenderType(this.getDefaultState()) == RenderBlocks.FULL_BLOCK){
            return this.texture[meta];
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(EnumFacing side, IBlockState state, BlockRenderLayer layer, IBlockAccess world, BlockPos pos) {
        TextureAtlasSprite texture = null;

        if(this.getIcon(world, pos, EnumFacing.NORTH) != null){
            texture = this.getIcon(world, pos, EnumFacing.NORTH);
        }
        else{
            texture = this.getIcon(BlockAccessUtils.getBlockMetadata(world, pos), EnumFacing.NORTH);
        }

        return texture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        CustomParticleHandler.addDestroyEffects(world, pos, manager, this);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(IBlockState state, World world, RayTraceResult target, ParticleManager manager) {
        CustomParticleHandler.addHitEffects(state, world, target, manager, this);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(EnumFacing side, ItemStack stack) {
        return TextureUtils.getMissingSprite();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorMultiplier(int meta, EnumFacing side) {
        return 0xFFFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public EnumBlockRenderType getRenderType(IBlockState state){
        return RenderBlocks.FULL_BLOCK;
    }

    @Override
    public void handlePreInit(FMLPreInitializationEvent event) {
        this.modid = event.getModMetadata().modId;
        this.setRegistryName(this.modid, this.blockName);
        this.setUnlocalizedName(this.modid + "." + this.blockName);
        GameRegistry.register(this);
        GameRegistry.register(this.getItemBlock().setRegistryName(this.getRegistryName()));
    }

    @Override
    public void handleInit(FMLInitializationEvent event) {
        this.registerTileEntities();
    }

    @Override
    public void handlePostInit(FMLPostInitializationEvent event) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientPreInit(FMLPreInitializationEvent event){
        RenderingRegistry.registerBlock(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientInit(FMLInitializationEvent event) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientPostInit(FMLPostInitializationEvent event) {

    }

    private SoundType getSoundType(Material material){
        if(material == Material.ANVIL){
            return SoundType.ANVIL;
        }
        else if(material == Material.CARPET || material == Material.CLOTH || material == Material.CAKE) {
            return SoundType.CLOTH;
        }
        else if(material == Material.GLASS || material == Material.ICE) {
            return SoundType.GLASS;
        }
        else if(material == Material.GRASS || material == Material.TNT || material == Material.PLANTS || material == Material.VINE) {
            return SoundType.PLANT;
        }
        else if(material == Material.GROUND) {
            return SoundType.GROUND;
        }
        else if(material == Material.IRON) {
            return SoundType.METAL;
        }
        else if(material == Material.SAND) {
            return SoundType.SAND;
        }
        else if(material == Material.SNOW) {
            return SoundType.SNOW;
        }
        else if(material == Material.ROCK) {
            return SoundType.STONE;
        }
        else if(material == Material.WOOD || material == Material.CACTUS) {
            return SoundType.WOOD;
        }
        else{
            return SoundType.STONE;
        }
    }

    public ItemBlock getItemBlock(){
        return new ItemBlockBase(this);
    }

    public String getModid(){
        return this.modid;
    }

    public String getBlockName(){
        return this.blockName;
    }

    public String[] getSubNames(){
        return this.subNames;
    }

    public CreativeTabs getCreativeTab(){
        return CreativeTabs.BUILDING_BLOCKS;
    }

    public void registerTileEntities(){}

    public PropertyDataHolder initializePropertyData(PropertyDataHolder dataHolder, IBlockAccess world, BlockPos pos){
        return dataHolder;
    }

}
