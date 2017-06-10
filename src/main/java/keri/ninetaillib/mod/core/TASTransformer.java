/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.mod.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.Arrays;

public class TASTransformer implements IClassTransformer {

    private static final String[] CLASSES = new String[]{
            "net.minecraft.client.renderer.texture.TextureAtlasSprite"
    };

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        boolean isObfuscated = !name.equals(transformedName);
        int index = Arrays.asList(CLASSES).indexOf(transformedName);
        return index != -1 ? this.transformTAS(index, basicClass, isObfuscated) : basicClass;
    }

    private byte[] transformTAS(int index, byte[] basicClass, boolean isObfuscated){
        if(index == 0){
            try{
                ClassNode classNode = new ClassNode();
                ClassReader classReader = new ClassReader(basicClass);
                classReader.accept(classNode, 0);
                classNode.interfaces.add("keri/ninetaillib/lib/texture/IIcon");
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                classNode.accept(classWriter);
                return classWriter.toByteArray();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        return basicClass;
    }

}
