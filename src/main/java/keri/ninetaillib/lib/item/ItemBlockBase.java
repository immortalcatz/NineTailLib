/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.item;

import keri.ninetaillib.lib.block.BlockBase;
import keri.ninetaillib.lib.util.ILocalization;
import keri.ninetaillib.lib.util.IShiftDescription;
import keri.ninetaillib.lib.util.Translations;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemBlockBase extends ItemBlock {

    public ItemBlockBase(Block block) {
        super(block);

        if(block instanceof BlockBase){
            this.setHasSubtypes(((BlockBase)block).getSubNames() != null);
        }
    }

    @Override
    public int getMetadata(int damage) {
        if(this.block instanceof BlockBase){
            return ((BlockBase)this.block).getSubNames() != null ? damage : super.getMetadata(damage);
        }
        else{
            return super.getMetadata(damage);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        this.block.getSubBlocks(item, tab, subItems);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) {
        if(this.block instanceof ILocalization){
            return ((ILocalization)this.block).getUnlocalizedName(stack);
        }
        else{
            if(this.block instanceof BlockBase){
                if(((BlockBase)this.block).getSubNames() != null){
                    return this.getUnlocalizedName() + "." + ((BlockBase)this.block).getSubNames()[stack.getMetadata()];
                }
                else{
                    return super.getUnlocalizedName(stack);
                }
            }
            else{
                return super.getUnlocalizedName(stack);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if(this.block instanceof IShiftDescription){
            if(((IShiftDescription)this.block).shouldAddDescription(stack, player)){
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
                    ((IShiftDescription)this.block).addDescription(stack, player, tooltip);
                }
                else{
                    String press = TextFormatting.GRAY + Translations.TOOLTIP_PRESS;
                    String shift = TextFormatting.YELLOW + Translations.TOOLTIP_SHIFT;
                    String info = TextFormatting.GRAY + Translations.TOOLTIP_INFO;
                    tooltip.add(press + " " + shift + " " + info);
                }
            }
        }
    }

}
