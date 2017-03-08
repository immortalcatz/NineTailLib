package keri.ninetaillib.property;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraftforge.common.property.IUnlistedProperty;

public class CommonProperties {

    public static final PropertyEnum<EnumDyeColor> DYE_COLOR = PropertyEnum.create("type", EnumDyeColor.class);
    public static final NBTTagProperty NBT_TAG_PROPERTY = new NBTTagProperty();

    /**
     * Will be removed in the next updates !
     */
    @Deprecated
    public static final IUnlistedProperty BLOCK_ORIENTATION = new IUnlistedProperty<BlockOrientation>(){

        @Override
        public String getName() {
            return "block_orientation";
        }

        @Override
        public boolean isValid(BlockOrientation value) {
            return value.getOrientation() != null;
        }

        @Override
        public Class<BlockOrientation> getType() {
            return BlockOrientation.class;
        }

        @Override
        public String valueToString(BlockOrientation value) {
            return value.getOrientation().getName();
        }

    };

}
