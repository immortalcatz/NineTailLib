/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.test.block;

import codechicken.lib.util.ItemUtils;
import keri.ninetaillib.lib.block.BlockBase;
import keri.ninetaillib.lib.util.EnumDyeColor;
import keri.ninetaillib.test.client.render.RenderItemPillar;
import keri.ninetaillib.test.tile.TileEntityItemPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockItemPillar extends BlockBase<TileEntityItemPillar> {

    public BlockItemPillar() {
        super("item_pillar", Material.ROCK, EnumDyeColor.toStringArray());
        this.setHardness(1.4F);
    }

    @Nullable
    @Override
    public TileEntityItemPillar createNewTileEntity(World world, int meta) {
        return new TileEntityItemPillar();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityItemPillar tile = (TileEntityItemPillar)world.getTileEntity(pos);

        if(tile != null){
            if(!world.isRemote){
                ItemStack heldItem = player.getHeldItemMainhand();

                if(!heldItem.isEmpty() && tile.getStackInSlot(0).isEmpty()){
                    tile.setInventorySlotContents(0, heldItem.copy());
                    heldItem.setCount(0);
                }
                else if(!tile.getStackInSlot(0).isEmpty()){
                    EntityItem entity = new EntityItem(world, player.posX, player.posY, player.posZ);
                    entity.setEntityItemStack(tile.getStackInSlot(0));
                    world.spawnEntity(entity);
                    tile.setInventorySlotContents(0, ItemStack.EMPTY);
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityItemPillar tile = (TileEntityItemPillar)world.getTileEntity(pos);

        if(tile != null && !world.isRemote){
            ItemUtils.dropInventory(world, pos, tile);
        }

        super.breakBlock(world, pos, state);
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
