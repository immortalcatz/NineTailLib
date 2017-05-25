/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.mod.core;

import codechicken.lib.asm.ASMBlock;
import codechicken.lib.asm.ModularASMTransformer;
import keri.ninetaillib.lib.util.ASMUtils;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public abstract class TransformerBase implements IClassTransformer {

    private ModularASMTransformer transformer = new ModularASMTransformer();

    public TransformerBase(){
        Map<String, ASMBlock> blocks = ASMUtils.readBlocks(this.getASMFile());
        this.transform(this.transformer, blocks);
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        return this.transformer.transform(name, basicClass);
    }

    public abstract void transform(ModularASMTransformer transformer, Map<String, ASMBlock> blocks);

    public abstract ResourceLocation getASMFile();

}
