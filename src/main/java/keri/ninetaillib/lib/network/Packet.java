package keri.ninetaillib.lib.network;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.mod.NineTailLib;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class Packet {

    private PacketCustom packet;

    public Packet(int type){
        this.packet = new PacketCustom(NineTailLib.INSTANCE, type);
    }

    public Packet(PacketCustom packet){
        this.packet = packet;
    }

    public void writeByte(byte value){
        this.packet.writeByte(value);
    }

    public void writeByteArray(byte[] value){
        this.packet.writeInt(value.length);

        for(int i = 0; i < value.length; i++){
            this.packet.writeByte(value[i]);
        }
    }

    public void writeInt(int value){
        this.packet.writeInt(value);
    }

    public void writeIntArray(int[] value){
        this.packet.writeInt(value.length);

        for(int i = 0; i < value.length; i++){
            this.packet.writeInt(value[i]);
        }
    }

    public void writeFloat(float value){
        this.packet.writeFloat(value);
    }

    public void writeFloatArray(float[] value){
        this.packet.writeInt(value.length);

        for(int i = 0; i < value.length; i++){
            this.packet.writeFloat(value[i]);
        }
    }

    public void writeLong(long value){
        this.packet.writeLong(value);
    }

    public void writeShort(short value){
        this.packet.writeShort(value);
    }

    public void writeDouble(double value){
        this.packet.writeDouble(value);
    }

    public void writeBoolean(boolean value){
        this.packet.writeBoolean(value);
    }

    public void writeBlockPos(BlockPos value){
        this.packet.writePos(value);
    }

    public void writeTag(NBTTagCompound value){
        this.packet.writeNBTTagCompound(value);
    }

    public byte readByte(){
        return this.packet.readByte();
    }

    public byte[] readByteArray(){
        final int size = this.packet.readInt();
        byte[] value = new byte[size];

        for(int i = 0; i < size; i++){
            value[i] = this.packet.readByte();
        }

        return value;
    }

    public int readInt(){
        return this.packet.readInt();
    }

    public int[] readIntArray(){
        final int size = this.packet.readInt();
        int[] value = new int[size];

        for(int i = 0; i < size; i++){
            value[i] = this.packet.readInt();
        }

        return value;
    }

    public float readFloat(){
        return this.packet.readFloat();
    }

    public float[] readFloatArray(){
        final int size = this.packet.readInt();
        float[] value = new float[size];

        for(int i = 0; i < size; i++){
            value[i] = this.packet.readFloat();
        }

        return value;
    }

    public long readLong(){
        return this.packet.readLong();
    }

    public short readShort(){
        return this.packet.readShort();
    }

    public double readDouble(){
        return this.packet.readDouble();
    }

    public boolean readBoolean(){
        return this.packet.readBoolean();
    }

    public BlockPos readBlockPos(){
        return this.packet.readPos();
    }

    public NBTTagCompound readTag(){
        return this.packet.readNBTTagCompound();
    }

    public void sendToClients(){
        this.packet.sendToClients();
    }

    public void sendToServer(){
        this.packet.sendToServer();
    }

    public void sendToAllAround(BlockPos pos, double dimensions, int dimension){
        this.packet.sendPacketToAllAround(pos, dimensions, dimension);
    }

}
