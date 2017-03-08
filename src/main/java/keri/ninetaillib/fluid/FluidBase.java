package keri.ninetaillib.fluid;

import keri.ninetaillib.internal.NineTailLib;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidBase extends Fluid {

    private String modid;

    public FluidBase(String modid, String fluidName) {
        super(
                fluidName,
                new ResourceLocation(modid, "blocks/fluid/" + fluidName + "_still"),
                new ResourceLocation(modid, "blocks/fluid/" + fluidName + "_flowing")
        );

        this.modid = modid;
        this.register();
    }

    private void register(){
        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
        NineTailLib.PROXY.handleFluid(this);
    }

    @Override
    public String getUnlocalizedName(){
        return "fluid." + this.modid + "." + this.unlocalizedName;
    }

    public String getModId(){
        return this.modid;
    }

}
