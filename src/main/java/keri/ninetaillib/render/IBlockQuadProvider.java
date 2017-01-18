package keri.ninetaillib.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;

import java.util.List;

public interface IBlockQuadProvider {

    List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long random);

}
