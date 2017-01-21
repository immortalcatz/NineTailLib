package keri.ninetaillib.mod;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.block.BlockBase;
import keri.ninetaillib.item.ItemBase;
import keri.ninetaillib.network.NineTailLibSPH;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements INineTailProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        PacketCustom.assignHandler(NineTailLib.INSTANCE, new NineTailLibSPH());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void handleBlock(BlockBase block) {

    }

    @Override
    public void handleItem(ItemBase item) {

    }

}
