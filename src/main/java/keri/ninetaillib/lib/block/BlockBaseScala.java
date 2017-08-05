/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public class BlockBaseScala<T extends TileEntity> extends BlockBase<T> {

    public BlockBaseScala(String blockName, Material material, MapColor mapColor) {
        super(blockName, material, mapColor);
    }

    public BlockBaseScala(String blockName, Material material) {
        super(blockName, material);
    }

    public BlockBaseScala(String blockName, Material material, MapColor mapColor, String[] subNames) {
        super(blockName, material, mapColor, subNames);
    }

    public BlockBaseScala(String blockName, Material material, String[] subNames) {
        super(blockName, material, subNames);
    }

}
