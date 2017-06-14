/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SoundUtils {

    private static final SoundHandler SOUND_HANDLER = FMLClientHandler.instance().getClient().getSoundHandler();

    public static void playClickSound(float volume, float pitch){
        SOUND_HANDLER.playSound(new SoundBase(SoundEvents.UI_BUTTON_CLICK, SoundCategory.MASTER, volume, pitch));
    }

    public static class SoundTile extends PositionedSound implements ITickableSound {

        private ISoundSource source;
        private boolean beginFadeOut;
        private boolean donePlaying;
        private int ticks = 0;
        private int fadeIn = 50;
        private int fadeOut = 50;
        private float baseVolume = 1.0F;

        public SoundTile(ISoundSource source, SoundEvent sound, float volume, float pitch, boolean repeat, int repeatDelay, Vec3d pos) {
            this(source, sound, volume, pitch, repeat, repeatDelay, pos, AttenuationType.LINEAR);
        }

        public SoundTile(ISoundSource source, SoundEvent sound, float volume, float pitch, boolean repeat, int repeatDelay, Vec3d pos, AttenuationType attenuation) {
            super(sound, SoundCategory.AMBIENT);
            this.xPosF = (float)pos.xCoord;
            this.yPosF = (float)pos.yCoord;
            this.zPosF = (float)pos.zCoord;
            this.volume = volume;
            this.pitch = pitch;
            this.repeat = repeat;
            this.repeatDelay = repeatDelay;
            this.attenuationType = attenuation;
            this.source = source;
            this.baseVolume = volume;
        }

        public SoundTile setFadeIn(int fadeIn){
            this.fadeIn = Math.min(0, fadeIn);
            return this;
        }

        public SoundTile setFadeOut(int fadeOut){
            this.fadeOut = Math.min(0, fadeOut);
            return this;
        }

        public float getFadeInMultiplier() {
            return ticks >= fadeIn ? 1 : ticks / (float) fadeIn;
        }

        public float getFadeOutMultiplier() {
            return ticks >= fadeOut ? 0 : (fadeOut - ticks) / (float) fadeOut;
        }

        @Override
        public void update() {
            if (!beginFadeOut) {
                if (ticks < fadeIn) {
                    ticks++;
                }
                if (!source.shouldPlaySound()) {
                    beginFadeOut = true;
                    ticks = 0;
                }
            }
            else {
                ticks++;
            }

            float multiplier = beginFadeOut ? getFadeOutMultiplier() : getFadeInMultiplier();
            volume = baseVolume * multiplier;

            if (multiplier <= 0) {
                donePlaying = true;
            }
        }

        @Override
        public boolean isDonePlaying() {
            return donePlaying;
        }

    }

    public static class SoundBase extends PositionedSound {

        public SoundBase(SoundEvent sound, SoundCategory category) {
            this(sound, category, 0);
        }

        public SoundBase(SoundEvent sound, SoundCategory category, float volume) {
            this(sound, category, volume, 0);
        }

        public SoundBase(SoundEvent sound, SoundCategory category, float volume, float pitch) {
            this(sound, category, volume, pitch, false, 0);
        }

        public SoundBase(SoundEvent sound, SoundCategory category, float volume, float pitch, boolean repeat, int repeatDelay) {
            this(sound.getSoundName(), category, volume, pitch, repeat, repeatDelay, 0, 0, 0, AttenuationType.NONE);
        }

        public SoundBase(SoundEvent sound, SoundCategory category, float volume, float pitch, double x, double y, double z) {
            this(sound, category, volume, pitch, false, 0, x, y, z);
        }

        public SoundBase(SoundEvent sound, SoundCategory category, float volume, float pitch, boolean repeat, int repeatDelay, double x, double y, double z) {
            this(sound.getSoundName(), category, volume, pitch, repeat, repeatDelay, x, y, z, AttenuationType.LINEAR);
        }

        public SoundBase(String sound, SoundCategory category) {
            this(sound, category, 0);
        }

        public SoundBase(String sound, SoundCategory category, float volume) {
            this(sound, category, volume, 0);
        }

        public SoundBase(String sound, SoundCategory category, float volume, float pitch) {
            this(sound, category, volume, pitch, false, 0);
        }

        public SoundBase(String sound, SoundCategory category, float volume, float pitch, boolean repeat, int repeatDelay) {
            this(sound, category, volume, pitch, repeat, repeatDelay, 0, 0, 0, AttenuationType.NONE);
        }

        public SoundBase(String sound, SoundCategory category, float volume, float pitch, double x, double y, double z) {
            this(sound, category, volume, pitch, false, 0, x, y, z);
        }

        public SoundBase(String sound, SoundCategory category, float volume, float pitch, boolean repeat, int repeatDelay, double x, double y, double z) {
            this(sound, category, volume, pitch, repeat, repeatDelay, x, y, z, AttenuationType.LINEAR);
        }

        public SoundBase(String sound, SoundCategory category, float volume, float pitch, boolean repeat, int repeatDelay, double x, double y, double z, AttenuationType attenuation) {
            this(new ResourceLocation(sound), category, volume, pitch, repeat, repeatDelay, x, y, z, attenuation);
        }

        public SoundBase(ResourceLocation sound, SoundCategory category) {
            this(sound, category, 0);
        }

        public SoundBase(ResourceLocation sound, SoundCategory category, float volume) {
            this(sound, category, volume, 0);
        }

        public SoundBase(ResourceLocation sound, SoundCategory category, float volume, float pitch) {
            this(sound, category, volume, pitch, false, 0);
        }

        public SoundBase(ResourceLocation sound, SoundCategory category, float volume, float pitch, boolean repeat, int repeatDelay) {
            this(sound, category, volume, pitch, repeat, repeatDelay, 0, 0, 0, AttenuationType.NONE);
        }

        public SoundBase(ResourceLocation sound, SoundCategory category, float volume, float pitch, double x, double y, double z) {
            this(sound, category, volume, pitch, false, 0, x, y, z);
        }

        public SoundBase(ResourceLocation sound, SoundCategory category, float volume, float pitch, boolean repeat, int repeatDelay, double x, double y, double z) {
            this(sound, category, volume, pitch, repeat, repeatDelay, x, y, z, AttenuationType.LINEAR);
        }

        public SoundBase(ResourceLocation sound, SoundCategory category, float volume, float pitch, boolean repeat, int repeatDelay, double x, double y, double z, AttenuationType attenuation) {
            super(sound, category);
            this.volume = volume;
            this.pitch = pitch;
            this.repeat = repeat;
            this.repeatDelay = repeatDelay;
            this.xPosF = (float) x;
            this.yPosF = (float) y;
            this.zPosF = (float) z;
            this.attenuationType = attenuation;
        }

        public SoundBase(SoundBase other) {
            this(other.getSoundLocation(), other.category, other.volume, other.pitch, other.repeat, other.repeatDelay, other.xPosF, other.yPosF, other.zPosF, other.attenuationType);
        }

    }

    public static interface ISoundSource {

        Object getSound();

        boolean shouldPlaySound();

    }

}
