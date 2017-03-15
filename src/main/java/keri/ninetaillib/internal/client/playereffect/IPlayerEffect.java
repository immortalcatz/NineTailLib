package keri.ninetaillib.internal.client.playereffect;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IPlayerEffect {

    void renderPlayerEffect(EntityPlayer player, float partialTicks);

}
