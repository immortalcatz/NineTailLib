/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.test.init;

import keri.ninetaillib.lib.mod.ContentLoader;
import keri.ninetaillib.test.TestMod;
import keri.ninetaillib.test.block.BlockItemPillar;
import keri.ninetaillib.test.item.ItemLavaOrb;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@ContentLoader(modid = TestMod.MODID)
public class TestContent {

    public static Block itemPillar = new BlockItemPillar();
    public static Item lavaOrb = new ItemLavaOrb();

}
