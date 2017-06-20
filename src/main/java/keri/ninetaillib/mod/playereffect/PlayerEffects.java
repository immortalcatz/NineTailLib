/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.mod.playereffect;

import java.util.UUID;

public class PlayerEffects {

    public static void preInit(){
        //AtomicBlom
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("58d506e2-7ee7-4774-8b22-c7a57eda488b"), new PlayerEffectSheep());
    }

}
