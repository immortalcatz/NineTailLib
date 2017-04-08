package keri.ninetaillib.render.block;

import net.minecraftforge.common.property.IExtendedBlockState;

public interface IBlockKeyProvider {

    String getExtendedBlockKey(IExtendedBlockState state);

}
