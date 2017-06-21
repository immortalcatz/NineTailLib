/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.mod.playereffect;

import codechicken.lib.math.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerEffectDragonsBreath implements IPlayerEffect {

    private int ticks = 0;

    @Override
    public void renderPlayerEffect(EntityPlayer player, float partialTicks) {
        World world = player.world;

        if(ticks < 4){
            this.ticks++;
        }
        else{
            double random = (double)(world.rand.nextInt(10) / 40D);

            for(int x = -3; x < 3; x++){
                for(int z = -3; z < 3; z++){
                    double posX = player.posX + (double)(x / 16D);
                    double posY = player.posY + random;
                    double posZ = player.posZ + (double)(z / 16D);
                    double moveX = 0D;
                    double moveY = player.capabilities.isFlying ? -0.05D : 0D;
                    double moveZ = 0D;
                    double angle = 12.25D * MathHelper.torad;
                    world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, posX, posY, posZ, moveX, moveY, moveZ, new int[0]);
                }
            }

            this.ticks = 0;
        }
    }

}
