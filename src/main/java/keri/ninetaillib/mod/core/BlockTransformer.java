/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.mod.core;

import codechicken.lib.asm.ASMBlock;
import codechicken.lib.asm.ModularASMTransformer;
import codechicken.lib.asm.ModularASMTransformer.MethodReplacer;
import codechicken.lib.asm.ObfMapping;
import keri.ninetaillib.lib.util.FileLocation;
import keri.ninetaillib.mod.util.ModPrefs;

import java.util.Map;

public class BlockTransformer extends TransformerBase {

    @Override
    public void transform(ModularASMTransformer transformer, Map<String, ASMBlock> blocks) {
        this.transformBlockFence(transformer, blocks);
        this.transformBlockWall(transformer, blocks);
        this.transformBlockPane(transformer, blocks);
    }

    @Override
    public FileLocation getASMFile() {
        return new FileLocation(ModPrefs.MODID, "asm/block_hooks");
    }

    private void transformBlockFence(ModularASMTransformer transformer, Map<String, ASMBlock> blocks){
        final String owner = "net/minecraft/block/BlockFence";
        ObfMapping canConnectTo = new ObfMapping(owner, "func_176524_e", "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Z");
        transformer.add(new MethodReplacer(canConnectTo, blocks.get("blockFence_n_canConnectTo"), blocks.get("blockFence_r_canConnectTo")));
    }

    private void transformBlockWall(ModularASMTransformer transformer, Map<String, ASMBlock> blocks){
        final String owner = "net/minecraft/block/BlockWall";
        ObfMapping canConnectTo = new ObfMapping(owner, "func_176253_e", "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Z");
        transformer.add(new MethodReplacer(canConnectTo, blocks.get("blockWall_n_canConnectTo"), blocks.get("blockWall_r_canConnectTo")));
    }

    private void transformBlockPane(ModularASMTransformer transformer, Map<String, ASMBlock> blocks){
        final String owner = "net/minecraft/block/BlockPane";
        ObfMapping canPaneConnectToBlock = new ObfMapping(owner, "func_150098_a", "(Lnet/minecraft/block/Block;)Z");
        transformer.add(new MethodReplacer(canPaneConnectToBlock, blocks.get("blockPane_n_canPaneConnectToBlock"), blocks.get("blockPane_r_canPaneConnectToBlock")));
    }

}
