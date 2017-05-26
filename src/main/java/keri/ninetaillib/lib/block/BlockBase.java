/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.block;

import keri.ninetaillib.lib.item.ItemBlockBase;
import keri.ninetaillib.lib.mod.IContentRegister;
import keri.ninetaillib.lib.render.RenderBlocks;
import keri.ninetaillib.lib.texture.IIconBlock;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockBase<T extends TileEntity> extends Block implements ITileEntityProvider, IContentRegister, IIconBlock {

    public static final PropertyInteger META_DATA = PropertyInteger.create("meta", 0, 15);
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
        this.setDefaultState(this.blockState.getBaseState().withProperty(META_DATA, 0));
    }

    public BlockBase(String blockName, Material material) {
        super(material);
        this.blockName = blockName;
        this.setCreativeTab(this.getCreativeTab());
        this.setSoundType(this.getSoundType(material));
        this.setDefaultState(this.blockState.getBaseState().withProperty(META_DATA, 0));
    }

    public BlockBase(String blockName, Material material, MapColor mapColor, String... subNames) {
        super(material, mapColor);
        this.blockName = blockName;
        this.subNames = subNames;
        this.setCreativeTab(this.getCreativeTab());
        this.setSoundType(this.getSoundType(material));
        this.setDefaultState(this.blockState.getBaseState().withProperty(META_DATA, 0));
    }

    public BlockBase(String blockName, Material material, String... subNames){
        super(material);
        this.blockName = blockName;
        this.subNames = subNames;
        this.setCreativeTab(this.getCreativeTab());
        this.setSoundType(this.getSoundType(material));
        this.setDefaultState(this.blockState.getBaseState().withProperty(META_DATA, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{META_DATA});
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META_DATA);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(META_DATA, meta);
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
    public void reigisterIcons(IIconRegister register){
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
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(IBlockAccess world, BlockPos pos, int side) {
        return null;
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
        if(this.hasMultipleTileEntities()){
            for(int i = 0; i < this.subNames.length; i++){
                Class<? extends TileEntity> tileClass = this.createNewTileEntity(null, i).getClass();
                String name = "tile." + this.modid + "." + this.blockName + "." + this.subNames[i];
                GameRegistry.registerTileEntity(tileClass, name);
            }
        }
        else{
            Class<? extends TileEntity> tileClass = this.createNewTileEntity(null, 0).getClass();
            String name = "tile." + this.modid + "." + this.blockName;
            GameRegistry.registerTileEntity(tileClass, name);
        }
    }

    @Override
    public void handlePostInit(FMLPostInitializationEvent event) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientPreInit(FMLPreInitializationEvent event) {

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

    public boolean hasMultipleTileEntities(){
        return false;
    }

    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab(){
        return CreativeTabs.BUILDING_BLOCKS;
    }

}
