package keri.ninetaillib.internal.handler;

import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.util.CommonUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Set;
import java.util.UUID;

public class CommonEventHandler {

    private final Set<Block> whitelist = Sets.newHashSet(
            Blocks.DIRT,
            Blocks.GRASS,
            Blocks.COBBLESTONE,
            Blocks.SAND
    );

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.PlaceEvent event){
        if(NineTailLib.CONFIG.enableDarkoEasteregg){
            this.darkoCake(event);
        }
    }

    private void darkoCake(BlockEvent.PlaceEvent event){
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        GameProfile profile = player != null ? player.getGameProfile() : CommonUtils.DEFAULT_PROFILE;

        if(FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld() != null){
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();

            if(profile.getId().equals(UUID.fromString("10755ea6-9721-467a-8b5c-92adf689072c"))){
                for(Block block : this.whitelist){
                    if(world.getBlockState(event.getPos()).getBlock() == block){
                        for(int y = -3; y < 3; y++){
                            for(int x = -3; x < 3; x++){
                                for(int z = -3; z < 3; z++){
                                    BlockPos currentPos = event.getPos().add(x, y, z);
                                    world.setBlockState(currentPos, Blocks.CAKE.getDefaultState(), 2);
                                    IBlockState state = world.getBlockState(currentPos);
                                    world.notifyBlockUpdate(currentPos, state, state, 3);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
