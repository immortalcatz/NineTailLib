package keri.ninetaillib.mod;

import keri.ninetaillib.block.BlockBase;
import keri.ninetaillib.item.ItemBase;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface INineTailProxy {

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void handleBlock(BlockBase block);

    void handleItem(ItemBase item);

}
