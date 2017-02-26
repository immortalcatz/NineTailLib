package keri.ninetaillib.block;

import keri.ninetaillib.util.HideInventory;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFluidBase extends BlockFluidClassic {

    private String modid;
    private String internalName;

    public BlockFluidBase(String modid, Fluid fluid) {
        super(fluid, Material.WATER);
        this.modid = modid;
        this.internalName = fluid.getName();
        this.register();
    }

    private void register(){
        this.setRegistryName(this.modid, this.internalName);
        this.setUnlocalizedName(this.modid + "." + this.internalName);
        this.setCreativeTab(this.getCreativeTab());

        if(super.getClass().isAnnotationPresent(HideInventory.class)){
            HideInventory annotation = super.getClass().getAnnotation(HideInventory.class);

            if(!annotation.onlySubtypes()){
                this.setCreativeTab((CreativeTabs)null);
            }
        }

        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public CreativeTabs getCreativeTab(){
        return CreativeTabs.SEARCH;
    }

    public String getModId(){
        return this.modid;
    }

}
