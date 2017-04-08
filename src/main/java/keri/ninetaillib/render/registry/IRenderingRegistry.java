package keri.ninetaillib.render.registry;

import keri.ninetaillib.block.BlockBase;
import keri.ninetaillib.fluid.FluidBase;
import keri.ninetaillib.item.ItemBase;
import net.minecraft.item.Item;

public interface IRenderingRegistry {

    void handleBlock(BlockBase block);

    void handleItem(ItemBase item);

    void handleFluid(FluidBase fluid);

    void handleItemSpecial(Item item);

}
