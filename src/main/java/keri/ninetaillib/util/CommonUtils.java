package keri.ninetaillib.util;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import keri.ninetaillib.fluid.FluidBase;
import keri.ninetaillib.item.IGuiItem;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class CommonUtils {

    public static final GameProfile DEFAULT_PROFILE = new GameProfile(UUID.fromString("8667ba71-b85a-4004-af54-457a9734eed7"), "Steve");

    public static SoundType getSoundType(Material material){
        if(material == Material.ANVIL){
            return SoundType.ANVIL;
        }
        else if(material == Material.CARPET || material == Material.CLOTH) {
            return SoundType.CLOTH;
        }
        else if(material == Material.GLASS || material == Material.ICE) {
            return SoundType.GLASS;
        }
        else if(material == Material.GRASS || material == Material.TNT || material == Material.PLANTS || material == Material.VINE) {
            return SoundType.PLANT;
        }
        else if(material == Material.GROUND) {
            return SoundType.GROUND;
        }
        else if(material == Material.IRON) {
            return SoundType.METAL;
        }
        else if(material == Material.SAND) {
            return SoundType.SAND;
        }
        else if(material == Material.SNOW) {
            return SoundType.SNOW;
        }
        else if(material == Material.ROCK) {
            return SoundType.STONE;
        }
        else if(material == Material.WOOD || material == Material.CACTUS) {
            return SoundType.WOOD;
        }
        else{
            return SoundType.STONE;
        }
    }

    public static boolean isShiftPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    public static boolean isControllPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    }

    public static AxisAlignedBB devide(AxisAlignedBB aabb, double d){
        double minX = aabb.minX / d;
        double minY = aabb.minX / d;
        double minZ = aabb.minX / d;
        double maxX = aabb.minX / d;
        double maxY = aabb.minX / d;
        double maxZ = aabb.minX / d;
        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static Cuboid6 devide(Cuboid6 cuboid, double d){
        double minX = cuboid.min.x / d;
        double minY = cuboid.min.y / d;
        double minZ = cuboid.min.z / d;
        double maxX = cuboid.max.x / d;
        double maxY = cuboid.max.y / d;
        double maxZ = cuboid.max.z / d;
        return new Cuboid6(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static Vector3 getWorldPosition(Entity entity, float partialTicks){
        double interpPosX = MathHelper.interpolate(entity.lastTickPosX, entity.posX, partialTicks);
        double interpPosY = MathHelper.interpolate(entity.lastTickPosY, entity.posY, partialTicks);
        double interpPosZ = MathHelper.interpolate(entity.lastTickPosZ, entity.posZ, partialTicks);
        double x = MathHelper.interpolate(entity.prevPosX, entity.posX, partialTicks) - interpPosX;
        double y = MathHelper.interpolate(entity.prevPosY, entity.posY, partialTicks) - interpPosY;
        double z = MathHelper.interpolate(entity.prevPosZ, entity.posZ, partialTicks) - interpPosZ;
        return new Vector3(x, y, z);
    }

    public static ColourRGBA readColorFromNBT(String name, NBTTagCompound tag){
        int[] colorData = tag.getIntArray(name);
        return new ColourRGBA(colorData[0], colorData[1], colorData[2], colorData[3]);
    }

    public static void writeColorToNBT(String name, NBTTagCompound tag, ColourRGBA color){
        int[] colorData = new int[4];
        colorData[0] = color.r;
        colorData[1] = color.g;
        colorData[2] = color.b;
        colorData[3] = color.a;
        tag.setIntArray(name, colorData);
    }

    public static ArrayList<BlockPos> readPosList(String name, NBTTagCompound tag){
        ArrayList<BlockPos> list = Lists.newArrayList();
        int[] xCoords = tag.getIntArray(name + "_x");
        int[] yCoords = tag.getIntArray(name + "_y");
        int[] zCoords = tag.getIntArray(name + "_z");

        for(int i = 0; i < xCoords.length; i++){
            list.add(new BlockPos(xCoords[i], yCoords[i], zCoords[i]));
        }

        return list;
    }

    public static void writePosList(String name, NBTTagCompound tag, ArrayList<BlockPos> list){
        int[] xCoords = new int[list.size()];
        int[] yCoords = new int[list.size()];
        int[] zCoords = new int[list.size()];

        for(int i = 0; i < list.size(); i++){
            xCoords[i] = list.get(i).getX();
            yCoords[i] = list.get(i).getY();
            zCoords[i] = list.get(i).getZ();
        }

        tag.setIntArray(name + "_x", xCoords);
        tag.setIntArray(name + "_y", yCoords);
        tag.setIntArray(name + "_z", zCoords);
    }

    public static void openItemGui(Object mod, EntityPlayer player, EntityEquipmentSlot slot){
        ItemStack stack = player.getItemStackFromSlot(slot);

        if(stack == null || !(stack.getItem() instanceof IGuiItem)){
            return;
        }

        IGuiItem iface = (IGuiItem)stack.getItem();
        player.openGui(mod, 100 * slot.ordinal() + iface.getGuiId(stack), player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
    }

    public static EntityEquipmentSlot getSlotFromGuiId(int ID){
        EntityEquipmentSlot slot = EntityEquipmentSlot.values()[ID / 100];
        ID %= 100;
        return slot;
    }

    public static boolean isDevEnvironment(){
        return (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

    public static ColourRGBA fromAWT(Color color){
        return new ColourRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color toAWT(ColourRGBA color){
        return new Color(color.rgba(), true);
    }

    public static Fluid makeFluid(String modid, String fluidName){
        FluidBase fluid = new FluidBase(modid, fluidName);
        return FluidRegistry.getFluid(fluid.getName());
    }

}
