/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.mod.core;

import codechicken.lib.asm.ASMBlock;
import codechicken.lib.asm.ModularASMTransformer;
import keri.ninetaillib.lib.util.ASMUtils;
import keri.ninetaillib.lib.util.FileLocation;
import net.minecraft.launchwrapper.IClassTransformer;

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

    public abstract FileLocation getASMFile();

}
