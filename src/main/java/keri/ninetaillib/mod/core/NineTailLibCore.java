/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.mod.core;

import com.google.common.eventbus.EventBus;
import keri.ninetaillib.mod.util.ModPrefs;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.Name(ModPrefs.NAME + " Core")
@IFMLLoadingPlugin.MCVersion(ModPrefs.ACC_MC)
public class NineTailLibCore extends DummyModContainer implements IFMLLoadingPlugin {

    public NineTailLibCore(){
        super(new ModMetadata());
        ModMetadata modMetadata = this.getMetadata();
        modMetadata.parent = ModPrefs.MODID;
        modMetadata.modId = ModPrefs.MODID + "_core";
        modMetadata.name = ModPrefs.NAME + " Core";
        modMetadata.version = ModPrefs.VERSION_CORE;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                "codechicken.lib.asm.ClassHierarchyManager",
                "keri.ninetaillib.mod.core.BlockTransformer",
                "keri.ninetaillib.mod.core.TASTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return "keri.ninetaillib.mod.core.NineTailLibCore";
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

}
