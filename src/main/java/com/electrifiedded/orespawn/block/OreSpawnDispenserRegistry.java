package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.ExtraRegistry;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.item.ItemSpawnEgg;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.item.Item;

/** Restores the projectile dispenser behavior registered by the original mod. */
public final class OreSpawnDispenserRegistry {
    private OreSpawnDispenserRegistry() {}

    public static void register() {
        IBehaviorDispenseItem eggBehavior = new DispenserBehaviorSpawnEgg();
        for (Item item : ExtraRegistry.ALL_ITEMS) {
            if (item instanceof ItemSpawnEgg) {
                BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, eggBehavior);
            }
        }

        put(OreSpawn.MyIrukandjiArrow, new MyDispenserBehaviorArrow());
        put(OreSpawn.MyWaterBall, new MyDispenserBehaviorWDCharge());
        put(OreSpawn.MySunspotUrchin, new MyDispenserBehaviorSunspotUrchin());
        put(OreSpawn.MyAcid, new MyDispenserBehaviorAcid());
        put(OreSpawn.MyIceBall, new MyDispenserBehaviorIceball());
        put(OreSpawn.MyIrukandji, new MyDispenserBehaviorDeadIrukandji());
        put(OreSpawn.MyLaserBall, new MyDispenserBehaviorLaserball());
        IBehaviorDispenseItem rockBehavior = new MyDispenserBehaviorRock();
        for (Item rock : new Item[]{OreSpawn.MySmallRock, OreSpawn.MyRock, OreSpawn.MyRedRock,
                OreSpawn.MyCrystalRedRock, OreSpawn.MyCrystalGreenRock, OreSpawn.MyCrystalBlueRock,
                OreSpawn.MyCrystalTNTRock, OreSpawn.MyBlueRock, OreSpawn.MyGreenRock,
                OreSpawn.MyPurpleRock, OreSpawn.MySpikeyRock, OreSpawn.MyTNTRock}) {
            put(rock, rockBehavior);
        }
    }

    private static void put(Item item, IBehaviorDispenseItem behavior) {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, behavior);
    }
}
