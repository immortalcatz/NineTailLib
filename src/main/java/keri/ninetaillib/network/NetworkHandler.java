package keri.ninetaillib.network;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.util.EnumDataType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

    public static void sendUpdatePacket(BlockPos pos, Side target, Object value, int valueId){
        PacketCustom packet = new PacketCustom(NineTailLib.INSTANCE, 1);
        packet.writeBlockPos(pos);
        packet.writeInt(valueId);

        if(value != null){
            if(value instanceof Integer){
                packet.writeInt(EnumDataType.INTEGER.getIndex());
                packet.writeInt((int)value);
            }
            else if(value instanceof Long){
                packet.writeInt(EnumDataType.LONG.getIndex());
                packet.writeLong((long)value);
            }
            else if(value instanceof Float){
                packet.writeInt(EnumDataType.FLOAT.getIndex());
                packet.writeFloat((float)value);
            }
            else if(value instanceof Double){
                packet.writeInt(EnumDataType.DOUBLE.getIndex());
                packet.writeDouble((double)value);
            }
            else if(value instanceof Boolean){
                packet.writeInt(EnumDataType.BOOLEAN.getIndex());
                packet.writeBoolean((boolean)value);
            }
            else if(value instanceof String){
                packet.writeInt(EnumDataType.STRING.getIndex());
                packet.writeString((String)value);
            }
            else if(value instanceof BlockPos){
                packet.writeInt(EnumDataType.BLOCK_POS.getIndex());
                packet.writeBlockPos((BlockPos)value);
            }
            else if(value instanceof FluidStack){
                packet.writeInt(EnumDataType.FLUID_STACK.getIndex());
                packet.writeFluidStack((FluidStack)value);
            }
            else if(value instanceof NBTTagCompound){
                packet.writeInt(EnumDataType.NBT_TAG_COMPOUND.getIndex());
                packet.writeNBTTagCompound((NBTTagCompound)value);
            }
            else{
                throw new IllegalArgumentException("The value you entered is not a supported type !");
            }
        }
        else{
            return;
        }

        switch(target){
            case CLIENT:
                packet.compress().sendToClients();
                break;
            case SERVER:
                packet.compress().sendToServer();
                break;
        }
    }

}
