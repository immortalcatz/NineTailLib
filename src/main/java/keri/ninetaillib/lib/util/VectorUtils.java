/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.util;

import codechicken.lib.math.MathHelper;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import net.minecraft.util.EnumFacing;

public class VectorUtils {

    public static Cuboid6 divide(Cuboid6 cuboid, double d){
        Vector3 min = cuboid.min.copy();
        Vector3 max = cuboid.max.copy();
        min.divide(d, d, d);
        max.divide(d, d, d);
        return new Cuboid6(min, max);
    }

    public static Cuboid6 rotate(Cuboid6 cuboid, double angle, EnumFacing.Axis axis){
        double angleRad = angle * MathHelper.torad;
        Vector3 axisVec = null;

        switch(axis){
            case X:
                axisVec = new Vector3(1D, 0D, 0D);
                break;
            case Y:
                axisVec = new Vector3(0D, 1D, 0D);
                break;
            case Z:
                axisVec = new Vector3(0D, 0D, 1D);
                break;
            default:
                axisVec = Vector3.zero;
                break;
        }

        Vector3 min = cuboid.min.copy().apply(new Rotation(angleRad, axisVec).at(Vector3.center));
        Vector3 max = cuboid.max.copy().apply(new Rotation(angleRad, axisVec).at(Vector3.center));
        return new Cuboid6(min, max);
    }

    public static Vector3 rotate(Vector3 vec, double angle, EnumFacing.Axis axis){
        double angleRad = angle * MathHelper.torad;
        Vector3 axisVec = null;

        switch(axis){
            case X:
                axisVec = new Vector3(1D, 0D, 0D);
                break;
            case Y:
                axisVec = new Vector3(0D, 1D, 0D);
                break;
            case Z:
                axisVec = new Vector3(0D, 0D, 1D);
                break;
            default:
                axisVec = Vector3.zero;
                break;
        }

        return vec.copy().apply(new Rotation(angleRad, axisVec).at(Vector3.center));
    }

}
