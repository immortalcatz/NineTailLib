package keri.ninetaillib.internal;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.block.BlockBase;
import keri.ninetaillib.internal.network.NineTailLibSPH;
import keri.ninetaillib.internal.proxy.INineTailProxy;
import keri.ninetaillib.item.ItemBase;
import keri.ninetaillib.render.registry.IRenderingRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static keri.ninetaillib.internal.util.ModPrefs.*;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPS, acceptedMinecraftVersions = ACC_MC)
public class NineTailLib {

    @Mod.Instance(value = MODID)
    public static NineTailLib INSTANCE = new NineTailLib();
    @SidedProxy(clientSide = CSIDE, serverSide = SSIDE)
    public static INineTailProxy PROXY;
    public static Logger LOGGER = LogManager.getLogger(NAME);

    public static ItemBase testItem;
    public static BlockBase testBlock;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        //this.preInitTest(event);
        PROXY.preInit(event);
        LOGGER.info("PreInitilization phase done !");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        PROXY.init(event);
        PacketCustom.assignHandler(NineTailLib.INSTANCE, new NineTailLibSPH());
        LOGGER.info("Initilization phase done !");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        PROXY.postInit(event);
        LOGGER.info("PostInitilization phase done !");
    }

    private void preInitTest(FMLPreInitializationEvent event){
        testItem = new ItemBase(MODID, "test_item") {
            @Override
            @SideOnly(Side.CLIENT)
            public IRenderingRegistry getRenderingRegistry() {
                return PROXY.getRenderingRegistry();
            }

            @Override
            @SideOnly(Side.CLIENT)
            public CreativeTabs getCreativeTab() {
                return CreativeTabs.MISC;
            }
        };
        testBlock = new BlockBase(MODID, "test_block", Material.ROCK) {
            @Override
            @SideOnly(Side.CLIENT)
            public IRenderingRegistry getRenderingRegistry() {
                return PROXY.getRenderingRegistry();
            }

            @Override
            @SideOnly(Side.CLIENT)
            public CreativeTabs getCreativeTab() {
                return CreativeTabs.MISC;
            }
        };
        testBlock.setTextureName("test_block");
        testBlock.setHardness(1.6F);
    }

}
