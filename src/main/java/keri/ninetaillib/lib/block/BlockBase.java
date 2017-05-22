/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockBase<T extends TileEntity> extends Block implements ITileEntityProvider {

    private String modid;
    private String blockName;

    public BlockBase(String blockName, Material material, MapColor mapColor) {
        super(material, mapColor);
        this.blockName = blockName;
        this.setRegistryName(modid, blockName);
        this.setUnlocalizedName(modid + "." + blockName);
        this.setCreativeTab(this.getCreativeTab());
        this.setSoundType(this.getSoundType(material));
    }

    public BlockBase(String blockName, Material material) {
        super(material);
        this.blockName = blockName;
        this.setRegistryName(modid, blockName);
        this.setUnlocalizedName(modid + "." + blockName);
        this.setCreativeTab(this.getCreativeTab());
        this.setSoundType(this.getSoundType(material));
    }

    @Nullable
    @Override
    public T createNewTileEntity(World world, int meta) {
        return null;
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
        return new ItemBlock(this);
    }

    public void setModid(String modid){
        this.modid = modid;
    }

    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab(){
        return CreativeTabs.BUILDING_BLOCKS;
    }

}
