package keri.ninetaillib.property;

import net.minecraft.block.properties.PropertyEnum;

public class CommonProperties {

    public static final PropertyEnum<EnumDyeColor> DYE_COLOR = PropertyEnum.create("type", EnumDyeColor.class);
    public static final NBTTagProperty NBT_TAG_PROPERTY = new NBTTagProperty();

}
