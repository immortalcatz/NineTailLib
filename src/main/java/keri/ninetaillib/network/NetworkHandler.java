package keri.ninetaillib.network;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.internal.NineTailLib;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

    public static void sendUpdatePacket(BlockPos pos, Side target, Object value, int valueId){
        PacketCustom packet = new PacketCustom(NineTailLib.INSTANCE, 1);
        packet.writeBlockPos(pos);
        packet.writeInt(valueId);

        if(value instanceof Integer){
            packet.writeInt(EnumDataType.INTEGER.ordinal());
            packet.writeInt((int)value);
        }
        else if(value instanceof Long){
            packet.writeInt(EnumDataType.LONG.ordinal());
            packet.writeLong((long)value);
        }
        else if(value instanceof Float){
            packet.writeInt(EnumDataType.FLOAT.ordinal());
            packet.writeFloat((float)value);
        }
        else if(value instanceof Double){
            packet.writeInt(EnumDataType.DOUBLE.ordinal());
            packet.writeDouble((double)value);
        }
        else if(value instanceof Boolean){
            packet.writeInt(EnumDataType.BOOLEAN.ordinal());
            packet.writeBoolean((boolean)value);
        }
        else if(value instanceof String){
            packet.writeInt(EnumDataType.STRING.ordinal());
            packet.writeString((String)value);
        }
        else if(value instanceof BlockPos){
            packet.writeInt(EnumDataType.BLOCK_POS.ordinal());
            packet.writeBlockPos((BlockPos)value);
        }
        else if(value instanceof FluidStack){
            packet.writeInt(EnumDataType.FLUID_STACK.ordinal());
            packet.writeFluidStack((FluidStack)value);
        }
        else if(value instanceof NBTTagCompound){
            packet.writeInt(EnumDataType.NBT_TAG_COMPOUND.ordinal());
            packet.writeNBTTagCompound((NBTTagCompound)value);
        }
        else{
            throw new IllegalArgumentException("The value you entered is not a supported type !");
        }

        switch(target){
            case CLIENT:
                packet.sendToClients();
                break;
            case SERVER:
                packet.sendToServer();
                break;
        }
    }

    public static enum EnumDataType {

        INTEGER,
        LONG,
        FLOAT,
        DOUBLE,
        BOOLEAN,
        STRING,
        BLOCK_POS,
        FLUID_STACK,
        NBT_TAG_COMPOUND

    }

}
