package keri.ninetaillib.property;

import net.minecraft.util.EnumFacing;

/**
 * Will be removed in the next updates !
 */
@Deprecated
public class BlockOrientation {

    private EnumFacing orientation;

    public BlockOrientation(EnumFacing orientation){
        this.orientation = orientation;
    }

    public EnumFacing getOrientation(){
        return this.orientation;
    }

    public EnumFacing getOpposite(){
        return this.orientation.getOpposite();
    }

    public void rotateAroundX(){
        this.orientation.rotateAround(EnumFacing.Axis.X);
    }

    public void rotateAroundY(){
        this.orientation.rotateAround(EnumFacing.Axis.Y);
    }

    public void rotateAroundZ(){
        this.orientation.rotateAround(EnumFacing.Axis.Z);
    }

}
