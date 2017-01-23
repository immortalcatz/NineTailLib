package keri.ninetaillib.render;

import net.minecraftforge.common.property.IExtendedBlockState;

public interface IBlockKeyProvider {

    String getExtendedBlockKey(IExtendedBlockState state);

}
