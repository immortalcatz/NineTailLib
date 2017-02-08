package keri.ninetaillib.tile;

import cofh.api.energy.EnergyStorage;
import net.minecraft.util.EnumFacing;

public interface IPowerReceiver {

    boolean canConnect(EnumFacing from);

    EnergyStorage getInternalStorage();

}
