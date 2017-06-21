/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.mod.playereffect;

import java.util.UUID;

public class PlayerEffects {

    public static void preInit(){
        //KitsuneAlex
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("b2ac8c03-d994-4805-9e0f-57fede63c04d"), new PlayerEffectWolf());
        //AtomicBlom
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("58d506e2-7ee7-4774-8b22-c7a57eda488b"), new PlayerEffectSheep());
        //LiteWolf101
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("1e2326e7-a592-4e11-9b4c-d0c930deeca3"), new PlayerEffectWolf());
    }

}
