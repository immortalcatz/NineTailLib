/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

public enum DataType {

    STRING(0, "string", String.class, Constants.NBT.TAG_STRING),
    BYTE(1, "byte", Byte.class, Constants.NBT.TAG_BYTE),
    INTEGER(2, "integer", Integer.class, Constants.NBT.TAG_INT),
    LONG(3, "long", Long.class, Constants.NBT.TAG_LONG),
    SHORT(4, "short", Short.class, Constants.NBT.TAG_SHORT),
    FLOAT(5, "float", Float.class, Constants.NBT.TAG_FLOAT),
    DOUBLE(6, "double", Double.class, Constants.NBT.TAG_DOUBLE),
    BOOLEAN(7, "boolean", Boolean.class, Constants.NBT.TAG_BYTE),
    NBT_TAG_COMPOUND(8, "nbt_tag_compound", NBTTagCompound.class, Constants.NBT.TAG_COMPOUND),
    BYTE_ARRAY(9, "byte_array", byte[].class, Constants.NBT.TAG_BYTE_ARRAY),
    INTEGER_ARRAY(10, "integer_array", int[].class, Constants.NBT.TAG_INT_ARRAY);

    public static final DataType[] VALUES = new DataType[]{
            STRING,
            BYTE,
            INTEGER,
            LONG,
            SHORT,
            FLOAT,
            DOUBLE,
            BOOLEAN,
            NBT_TAG_COMPOUND
    };
    private int index;
    private String name;
    private Class<?> typeClass;
    private int nbtIndex;

    DataType(int index, String name, Class<?> typeClass, int nbtIndex){
        this.index = index;
        this.name = name;
        this.typeClass = typeClass;
        this.nbtIndex = nbtIndex;
    }

    public int getIndex(){
        return this.index;
    }

    public String getName(){
        return this.name;
    }

    public Class<?> getTypeClass(){
        return this.typeClass;
    }

    public int getNBTIndex(){
        return this.nbtIndex;
    }

}
