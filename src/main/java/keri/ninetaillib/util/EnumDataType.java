package keri.ninetaillib.util;

public enum EnumDataType {

    INTEGER(0),
    LONG(1),
    FLOAT(2),
    DOUBLE(3),
    BOOLEAN(4),
    STRING(5),
    BLOCK_POS(6),
    FLUID_STACK(7),
    NBT_TAG_COMPOUND(8);

    public static final EnumDataType[] VALUES = new EnumDataType[]{INTEGER, LONG, FLOAT, DOUBLE, BOOLEAN, STRING, BLOCK_POS, FLUID_STACK, NBT_TAG_COMPOUND};
    private int index;

    EnumDataType(int index){
        this.index = index;
    }

    public int getIndex(){
        return this.index;
    }

}
