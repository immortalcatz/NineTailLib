/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.mod.playereffect;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class PlayerEffects {

    public static void preInit(){
        //AtomicBlom
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("58d506e2-7ee7-4774-8b22-c7a57eda488b"), new PlayerEffectSheep());
        //LiteWolf101
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("1e2326e7-a592-4e11-9b4c-d0c930deeca3"), new PlayerEffectWolf());
        //Caro_Kazooie
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("7d91cce0-70d8-4f1f-9f79-62fc132d4c55"), new PlayerEffectItem(new ItemStack(Items.SLIME_BALL, 1, 0)));
        //direwolf20
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("bbb87dbe-690f-4205-bdc5-72ffb8ebc29d"), new PlayerEffectItem(new ItemStack(Items.POTATO, 1, 0)));
        //Adubbz
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("e358f277-4a4c-42f6-9ad8-3addf4869ea6"), new PlayerEffectItem(new ItemStack(Blocks.RED_FLOWER, 1, 0)));
        //Darkosto
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("10755ea6-9721-467a-8b5c-92adf689072c"), new PlayerEffectItem(new ItemStack(Blocks.CAKE, 1, 0)));
        //MrTJP
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("8fd63777-f437-49e6-9b96-54e98f01f74a"), new PlayerEffectItem(new ItemStack(Items.REDSTONE, 1, 0)));
        //modmuss50
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("cb7afd42-6488-4bb9-9dd3-151aa66d7049"), new PlayerEffectItem(new ItemStack(Blocks.ANVIL, 1, 0)));
        //SethBling (as if he would ever use this mod lol)
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("55a2e72e-0161-4c85-b191-a0b227ff758a"), new PlayerEffectItem(new ItemStack(Items.REDSTONE, 1, 0)));
    }

}
