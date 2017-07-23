/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.property;

import net.minecraft.nbt.NBTTagCompound;

public class PropertyDataHolder {

    private NBTTagCompound tag;

    public PropertyDataHolder(){
        this.tag = new NBTTagCompound();
    }

    public <T> T readData(IDataReader<T> reader){
        return reader.readData(this.tag);
    }

    public void writeData(IDataWriter writer){
        writer.writeData(this.tag);
    }

    @Override
    public String toString() {
        return this.tag.toString();
    }

}
