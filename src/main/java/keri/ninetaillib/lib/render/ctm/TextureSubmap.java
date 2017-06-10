/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.ctm;

import com.google.common.collect.Lists;
import keri.ninetaillib.lib.hooks.IIcon;
import lombok.experimental.Delegate;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class TextureSubmap implements IIcon, ISubmap {

    private static List<TextureSubmap> SUBMAPS = Lists.newArrayList();
    private static TextureSubmap DUMMY = new TextureSubmap(null, 0, 0);

    static{
        MinecraftForge.EVENT_BUS.register(DUMMY);
    }

    private int width;
    private int height;
    @Delegate
    private IIcon baseIcon;
    protected  IIcon[][] icons;

    public TextureSubmap(IIcon baseIcon, int width, int height) {
        this.baseIcon = baseIcon;
        this.width = width;
        this.height = height;
        this.icons = new IIcon[width][height];
        SUBMAPS.add(this);
    }

    @SubscribeEvent
    public final void TexturesStitched(TextureStitchEvent.Post event) {
        for (TextureSubmap ts : SUBMAPS) {
            ts.texturesStitched();
        }
    }

    public IIcon getBaseIcon() {
        return baseIcon;
    }

    public IIcon[][] getAllIcons() {
        IIcon[][] ret = ArrayUtils.clone(icons);
        for (int i = 0; i < ret.length; i++) {
            ret[i] = ArrayUtils.clone(ret[i]);
        }
        return ret;
    }

    @Override
    public IIcon getSubIcon(int x, int y) {
        x = x % icons.length;
        return icons[x][y % icons[x].length];
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void texturesStitched() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                icons[x][y] = new TextureVirtual(getBaseIcon(), width, height, x, y);
            }
        }
    }

    private class TextureVirtual implements IIcon {

        private int width;
        private int height;
        private float umin;
        private float umax;
        private float vmin;
        private float vmax;
        private String name;
        private IIcon parentIcon;

        private TextureVirtual(IIcon parent, int w, int h, int x, int y) {
            parentIcon = parent;
            umin = parentIcon.getInterpolatedU(16.0 * (x) / w);
            umax = parentIcon.getInterpolatedU(16.0 * (x + 1) / w);
            vmin = parentIcon.getInterpolatedV(16.0 * (y) / h);
            vmax = parentIcon.getInterpolatedV(16.0 * (y + 1) / h);
            name = parentIcon.getIconName() + "|" + x + "." + y;
            width = parentIcon.getIconWidth();
            height = parentIcon.getIconHeight();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMinU() {
            return umin;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMaxU() {
            return umax;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getInterpolatedU(double d0) {
            return (float) (umin + (umax - umin) * d0 / 16.0);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMinV() {
            return vmin;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMaxV() {
            return vmax;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getInterpolatedV(double d0) {
            return (float) (vmin + (vmax - vmin) * d0 / 16.0);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getIconName() {
            return name;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public int getIconWidth() {
            return width;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public int getIconHeight() {
            return height;
        }

    }

}
