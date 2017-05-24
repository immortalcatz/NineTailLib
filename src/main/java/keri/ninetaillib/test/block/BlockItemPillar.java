/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.test.block;

import keri.ninetaillib.lib.block.BlockBase;
import keri.ninetaillib.lib.util.EnumDyeColor;
import keri.ninetaillib.test.client.render.RenderItemPillar;
import keri.ninetaillib.test.tile.TileEntityItemPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockItemPillar extends BlockBase<TileEntityItemPillar> {

    private static final String blockName = "item_pillar";

    public BlockItemPillar() {
        super(blockName, Material.ROCK, EnumDyeColor.toStringArray());
        this.setHardness(1.4F);
    }

    @Nullable
    @Override
    public TileEntityItemPillar createNewTileEntity(World world, int meta) {
        return new TileEntityItemPillar();
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderItemPillar.RENDER_TYPE;
    }

}
