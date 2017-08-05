/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package baubles.api.cap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class BaublesContainerProvider implements INBTSerializable<NBTTagCompound>, ICapabilityProvider {

	private final BaublesContainer container;
	
	public BaublesContainerProvider(BaublesContainer container) {        
        this.container = container;
    }
	
	@Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {        
        return capability == BaublesCapabilities.CAPABILITY_BAUBLES;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {        
        if (capability == BaublesCapabilities.CAPABILITY_BAUBLES) return (T) this.container;            
        return null;
    }
    
    @Override
    public NBTTagCompound serializeNBT () {        
        return this.container.serializeNBT();
    }
    
    @Override
    public void deserializeNBT (NBTTagCompound nbt) {        
        this.container.deserializeNBT(nbt);
    }

}
