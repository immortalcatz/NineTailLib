package keri.ninetaillib.block;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IOrientableBlock {

    boolean isOrientable(World world, BlockPos pos, EntityLivingBase placer);

}
