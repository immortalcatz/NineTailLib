package keri.ninetaillib.util;

import keri.ninetaillib.fluid.FluidBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

    private ModelResourceLocation location;

    public FluidStateMapper(FluidBase fluid) {
        super();
        this.location = new ModelResourceLocation(new ResourceLocation(fluid.getModId(), "fluids"), fluid.getName());
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        return this.location;
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        return this.location;
    }

}
