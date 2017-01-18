package keri.ninetaillib.render;

import net.minecraftforge.common.property.IExtendedBlockState;

public interface ICacheKeyProvider {

    String getExtendedKey(IExtendedBlockState state);

}
