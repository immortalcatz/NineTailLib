package keri.ninetaillib.block;

import keri.ninetaillib.item.ItemBlockBase;
import keri.ninetaillib.mod.NineTailLib;
import keri.ninetaillib.render.DefaultBlockRenderer;
import keri.ninetaillib.render.IBlockRenderingHandler;
import keri.ninetaillib.texture.IIconBlock;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.ninetaillib.util.CommonUtils;
import keri.ninetaillib.util.HideInventory;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockBase<T extends TileEntity> extends Block implements ITileEntityProvider, IIconBlock {

    private String internalName;
    private String modid;
    private float[] hardness = null;
    private float[] resistance = null;
    private Material[] material = null;
    private boolean isFullBlock = true;
    private MapColor[] mapColor = null;
    private String textureName;

    public BlockBase(String modid, String blockName, Material material, MapColor mapColor) {
        super(material, mapColor);
        this.internalName = blockName;
        this.modid = modid;
        this.setRegistryName(modid, blockName);
        this.setUnlocalizedName(modid + "." + blockName);
        this.setCreativeTab(this.getCreativeTab());

        if(super.getClass().isAnnotationPresent(HideInventory.class)){
            HideInventory annotation = super.getClass().getAnnotation(HideInventory.class);

            if(!annotation.onlySubtypes()){
                this.setCreativeTab((CreativeTabs) null);
            }
        }

        NineTailLib.PROXY.handleBlock(this);
        GameRegistry.register(this);
        GameRegistry.register(this.getItemBlock().setRegistryName(this.getRegistryName()));
    }

    public BlockBase(String modid, String blockName, Material material) {
        super(material);
        this.internalName = blockName;
        this.modid = modid;
        this.setRegistryName(modid, blockName);
        this.setUnlocalizedName(modid + "." + blockName);
        this.setCreativeTab(this.getCreativeTab());

        if(super.getClass().isAnnotationPresent(HideInventory.class)){
            HideInventory annotation = super.getClass().getAnnotation(HideInventory.class);

            if(!annotation.onlySubtypes()){
                this.setCreativeTab((CreativeTabs)null);
            }
        }

        NineTailLib.PROXY.handleBlock(this);
        GameRegistry.register(this);
        GameRegistry.register(this.getItemBlock().setRegistryName(this.getRegistryName()));
    }

    @Override
    public T createNewTileEntity(World world, int meta) {
        return null;
    }

    @Override
    public int damageDropped(IBlockState state) {
        if(this instanceof IMetaBlock){
            return this.getMetaFromState(state);
        }
        else{
            return super.damageDropped(state);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        if(this instanceof IMetaBlock){
            IMetaBlock iface = (IMetaBlock)this;

            if(super.getClass().isAnnotationPresent(HideInventory.class)){
                HideInventory annotation = super.getClass().getAnnotation(HideInventory.class);

                if(annotation.onlySubtypes()){
                    list.add(new ItemStack(item, 1, 0));
                }
                else{
                    for(int i = 0; i < iface.getSubNames().length; i++){
                        list.add(new ItemStack(item, 1, i));
                    }
                }
            }
            else{
                for(int i = 0; i < iface.getSubNames().length; i++){
                    list.add(new ItemStack(item, 1, i));
                }
            }
        }
        else{
            list.add(new ItemStack(item, 1, 0));
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        if(this instanceof IMetaBlock && this.hardness != null){
            return this.hardness[this.getMetaFromState(state)];
        }
        else{
            return super.getBlockHardness(state, world, pos);
        }
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        if(this instanceof IMetaBlock && this.resistance != null){
            return this.resistance[this.getMetaFromState(world.getBlockState(pos))];
        }
        else{
            return super.getExplosionResistance(world, pos, exploder, explosion);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public Material getMaterial(IBlockState state) {
        if(this instanceof IMetaBlock && this.material != null){
            return this.material[this.getMetaFromState(state)];
        }
        else{
            return super.getMaterial(state);
        }
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return CommonUtils.getSoundType(this.getMaterial(state));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        if(this instanceof IMetaBlock){
            return new ItemStack(this, 1, this.getMetaFromState(state));
        }
        else{
            return super.getPickBlock(state, target, world, pos, player);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public MapColor getMapColor(IBlockState state) {
        if(this instanceof IMetaBlock && this.mapColor != null){
            return this.mapColor[this.getMetaFromState(state)];
        }
        else{
            return super.getMapColor(state);
        }
    }

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite texture;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegistrar registrar) {
        if(this.textureName != null){
            this.texture = registrar.registerIcon(this.modid + ":blocks/" + this.textureName);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture;
    }

    public void setTextureName(String textureName){ this.textureName = textureName; }

    public void setHardness(float[] hardness){ this.hardness = hardness; }

    public void setResistance(float[] resistance){ this.resistance = resistance; }

    public void setMaterial(Material[] material){ this.material = material; }

    public void setIsFullBlock(boolean isFullBlock){ this.isFullBlock = isFullBlock; }

    public void setMapColor(MapColor[] mapColor){ this.mapColor = mapColor; }

    public ItemBlock getItemBlock(){ return new ItemBlockBase(this); }

    public Material[] getMaterial(){ return this.material; }

    public String getInternalName(){ return this.internalName; }

    public CreativeTabs getCreativeTab(){ return CreativeTabs.SEARCH; }

    public IBlockRenderingHandler getRenderingHandler(){ return new DefaultBlockRenderer(); }

}
