package keri.ninetaillib.property;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.property.IUnlistedProperty;

public class NBTTagProperty implements IUnlistedProperty<NBTTagCompound> {

    @Override
    public String getName() {
        return "nbt_tag";
    }

    @Override
    public boolean isValid(NBTTagCompound value) {
        return true;
    }

    @Override
    public Class<NBTTagCompound> getType() {
        return NBTTagCompound.class;
    }

    @Override
    public String valueToString(NBTTagCompound value) {
        return value.toString();
    }

}
