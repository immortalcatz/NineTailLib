/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.nbt;

import keri.ninetaillib.lib.util.DataType;
import net.minecraft.nbt.*;

import java.util.HashMap;
import java.util.Map;

public class NBTHashMap<V> extends HashMap<String, V> {

    public NBTTagCompound serialize(String name, NBTTagCompound tag){
        if(!this.isEmpty()){
            NBTTagList tagList = new NBTTagList();
            DataType dataType = null;
            int index = 0;

            for(Map.Entry<String, V> entry : this.entrySet()){
                if(entry.getValue() instanceof String){
                    if(dataType == null){
                        dataType = DataType.STRING;
                    }

                    tagList.set(index, new NBTTagString(entry.getKey()));
                    tagList.set(index + 1, new NBTTagString((String)entry.getValue()));
                }
                else if(entry.getValue() instanceof Byte){
                    if(dataType == null){
                        dataType = DataType.BYTE;
                    }

                    tagList.set(index, new NBTTagString(entry.getKey()));
                    tagList.set(index + 1, new NBTTagByte((Byte)entry.getValue()));
                }
                else if(entry.getValue() instanceof Integer){
                    if(dataType == null){
                        dataType = DataType.INTEGER;
                    }

                    tagList.set(index, new NBTTagString(entry.getKey()));
                    tagList.set(index + 1, new NBTTagInt((Integer)entry.getValue()));
                }
                else if(entry.getValue() instanceof Long){
                    if(dataType == null){
                        dataType = DataType.LONG;
                    }

                    tagList.set(index, new NBTTagString(entry.getKey()));
                    tagList.set(index + 1, new NBTTagLong((Long)entry.getValue()));
                }
                else if(entry.getValue() instanceof Short){
                    if(dataType == null){
                        dataType = DataType.SHORT;
                    }

                    tagList.set(index, new NBTTagString(entry.getKey()));
                    tagList.set(index + 1, new NBTTagShort((Short)entry.getValue()));
                }
                else if(entry.getValue() instanceof Float){
                    if(dataType == null){
                        dataType = DataType.FLOAT;
                    }

                    tagList.set(index, new NBTTagString(entry.getKey()));
                    tagList.set(index + 1, new NBTTagFloat((Float)entry.getValue()));
                }
                else if(entry.getValue() instanceof Double){
                    if(dataType == null){
                        dataType = DataType.DOUBLE;
                    }

                    tagList.set(index, new NBTTagString(entry.getKey()));
                    tagList.set(index + 1, new NBTTagDouble((Double)entry.getValue()));
                }
                else if(entry.getValue() instanceof Boolean){
                    if(dataType == null){
                        dataType = DataType.BOOLEAN;
                    }

                    tagList.set(index, new NBTTagString(entry.getKey()));
                    byte value = (Boolean)entry.getValue() == true ? (byte)1 : 0;
                    tagList.set(index + 1, new NBTTagByte(value));
                }
                else if(entry.getValue() instanceof NBTTagCompound){
                    if(dataType == null){
                        dataType = DataType.NBT_TAG_COMPOUND;
                    }

                    tagList.set(index, new NBTTagString(entry.getKey()));
                    tagList.set(index + 1, (NBTTagCompound)entry.getValue());
                }
                else if(entry.getValue() instanceof byte[]){
                    if(dataType == null){
                        dataType = DataType.BYTE_ARRAY;
                    }

                    tagList.set(index, new NBTTagString(entry.getKey()));
                    tagList.set(index + 1, new NBTTagByteArray((byte[])entry.getValue()));
                }
                else if(entry.getValue() instanceof int[]){
                    if(dataType == null){
                        dataType = DataType.INTEGER_ARRAY;
                    }

                    tagList.set(index, new NBTTagString(entry.getKey()));
                    tagList.set(index + 1, new NBTTagIntArray((int[])entry.getValue()));
                }

                index += 2;
            }

            tag.setInteger(name + "_data_type", dataType.getIndex());
            tag.setTag(name, tagList);
        }

        return tag;
    }

    public void deserialize(String name, NBTTagCompound tag){
        if(tag.hasKey(name)){
            DataType dataType = DataType.VALUES[tag.getInteger(name + "_data_type")];

            NBTTagList tagList = tag.getTagList(name, dataType.getNBTIndex());

            for(int i = 0; i < tagList.tagCount() * 2; i += 2){
                String key = ((NBTTagString)tagList.get(i)).getString();

                switch(dataType){
                    case STRING:
                        String mapString = ((NBTTagString)tagList.get(i + 1)).getString();
                        this.put(key, (V)mapString);
                        break;
                    case BYTE:
                        Byte mapByte = ((NBTTagByte)tagList.get(i + 1)).getByte();
                        this.put(key, (V)mapByte);
                        break;
                    case INTEGER:
                        Integer mapInteger = ((NBTTagInt)tagList.get(i + 1)).getInt();
                        this.put(key, (V)mapInteger);
                        break;
                    case LONG:
                        Long mapLong = ((NBTTagLong)tagList.get(i + 1)).getLong();
                        this.put(key, (V)mapLong);
                        break;
                    case SHORT:
                        Short mapShort = ((NBTTagShort)tagList.get(i + 1)).getShort();
                        this.put(key, (V)mapShort);
                        break;
                    case FLOAT:
                        Float mapFloat = ((NBTTagFloat)tagList.get(i + 1)).getFloat();
                        this.put(key, (V)mapFloat);
                        break;
                    case DOUBLE:
                        Double mapDouble = ((NBTTagDouble)tagList.get(i + 1)).getDouble();
                        this.put(key, (V)mapDouble);
                        break;
                    case BOOLEAN:
                        Boolean mapBoolean = ((NBTTagByte)tagList.get(i + 1)).getByte() == 1 ? true : false;
                        this.put(key, (V)mapBoolean);
                        break;
                    case NBT_TAG_COMPOUND:
                        NBTTagCompound mapTagCompound = (NBTTagCompound)tagList.get(i + 1);
                        this.put(key, (V)mapTagCompound);
                        break;
                    case BYTE_ARRAY:
                        byte[] mapByteArray = ((NBTTagByteArray)tagList.get(i + 1)).getByteArray();
                        this.put(key, (V)mapByteArray);
                        break;
                    case INTEGER_ARRAY:
                        int[] mapIntegerArray = ((NBTTagIntArray)tagList.get(i + 1)).getIntArray();
                        this.put(key, (V)mapIntegerArray);
                        break;
                }
            }
        }
    }

}
