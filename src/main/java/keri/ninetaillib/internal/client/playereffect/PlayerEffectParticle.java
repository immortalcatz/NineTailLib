package keri.ninetaillib.internal.client.playereffect;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerEffectParticle implements IPlayerEffect {

    private EnumParticleTypes particleType;
    private double ySpeed;
    private int ticker = 0;

    public PlayerEffectParticle(EnumParticleTypes particle, double ySpeed){
        this.particleType = particle;
        this.ySpeed = ySpeed;
    }

    @Override
    public void renderPlayerEffect(EntityPlayer player, float partialTicks) {
        if(ticker < 6){
            this.ticker++;
        }
        else{
            if(!player.onGround){
                for(int y = 0; y < 2; y++){
                    for(int x = 0; x < 6; x++){
                        for(int z = 0; z < 6; z++){
                            double radius = 0.16D;
                            double posX = player.posX + (radius * x) - 0.25D;
                            double posY = player.posY + (radius * y) - 0.1D;
                            double posZ = player.posZ + (radius * z) - 0.25D;
                            player.worldObj.spawnParticle(this.particleType, posX, posY, posZ, 0D, this.ySpeed, 0D, new int[0]);
                        }
                    }
                }
            }
            else{
                double posX = player.posX;
                double posY = player.posY;
                double posZ = player.posZ;
                player.worldObj.spawnParticle(this.particleType, posX, posY, posZ, 0D, this.ySpeed / 3D, 0D, new int[0]);
            }

            this.ticker = 0;
        }
    }

}
