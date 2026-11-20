package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.OreSpawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

/**
 * 1.12.2-compatible port of the 1.7.10 OreSpawnTeleporter.
 * Finds the highest safe ground spot and puts the entity there.
 */
public class OreSpawnTeleporter extends Teleporter {
    private final WorldServer world;
    private final World oldWorld;
    private final Random random;
    private final int newdim;

    public OreSpawnTeleporter(WorldServer par1WorldServer, int dim, World par2World) {
        super(par1WorldServer);
        this.world = par1WorldServer;
        this.oldWorld = par2World;
        this.random = new Random(par1WorldServer.getSeed());
        this.newdim = dim;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        justPutMe(entity);
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
        justPutMe(entity);
        return true;
    }

    @Override
    public boolean makePortal(Entity entity) {
        return true;
    }

    private boolean isGroundBlock(net.minecraft.block.Block bid) {
        if (bid == net.minecraft.init.Blocks.AIR) return false;
        if (bid == net.minecraft.init.Blocks.DIRT) return true;
        if (bid == net.minecraft.init.Blocks.GRASS) return true;
        if (bid == net.minecraft.init.Blocks.STONE) return true;
        if (bid == net.minecraft.init.Blocks.END_STONE) return true;
        if (bid == net.minecraft.init.Blocks.NETHERRACK) return true;
        if (bid == net.minecraft.init.Blocks.COBBLESTONE) return true;
        if (bid == net.minecraft.init.Blocks.SAND) return true;
        if (bid == net.minecraft.init.Blocks.SANDSTONE) return true;
        return bid == net.minecraft.init.Blocks.FARMLAND;
    }

    public boolean justPutMe(Entity entity) {
        double oldX = entity.posX;
        double oldY = entity.posY;
        double oldZ = entity.posZ;
        int originalX = (int) entity.posX;
        int originalZ = (int) entity.posZ;
        int posX = originalX;
        int posZ = originalZ;
        int found = 0;
        int posY = Math.min(180, this.world.getActualHeight() - 2);
        int airfound = 0;
        int inarow = 0;

        for (int i = 0; i < 1000 && found == 0; i++) {
            posY = Math.min(180, this.world.getActualHeight() - 2);
            while (posY > 1) {
                net.minecraft.block.Block above = this.world.getBlockState(new BlockPos(posX, posY + 1, posZ)).getBlock();
                if (above != net.minecraft.init.Blocks.AIR) {
                    if (isGroundBlock(above)) inarow++;
                    if (airfound != 0 && inarow >= 3) break;
                } else {
                    inarow = 0;
                    net.minecraft.block.Block current = this.world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
                    if (current == net.minecraft.init.Blocks.AIR) {
                        airfound = 1;
                        net.minecraft.block.Block below = this.world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock();
                        if (below != net.minecraft.init.Blocks.AIR && below != null) {
                            if (this.world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getMaterial().isSolid()) {
                                found = 1;
                            } else if (below == net.minecraft.init.Blocks.TALLGRASS &&
                                       this.world.getBlockState(new BlockPos(posX, posY - 2, posZ)).getMaterial().isSolid()) {
                                found = 1;
                                posY--;
                            }
                            break;
                        }
                    }
                }
                posY--;
            }
            if (found == 0) {
                int spread = 3 + i / 5;
                posX = originalX + this.world.rand.nextInt(spread) - this.world.rand.nextInt(spread);
                posZ = originalZ + this.world.rand.nextInt(spread) - this.world.rand.nextInt(spread);
                if (i > 100) {
                    int extra = 2 + i / 5;
                    posX += OreSpawn.OreSpawnRand.nextInt(extra) - OreSpawn.OreSpawnRand.nextInt(extra);
                    posZ += OreSpawn.OreSpawnRand.nextInt(extra) - OreSpawn.OreSpawnRand.nextInt(extra);
                }
                if (i > 500) {
                    int extra = 2 + i / 5;
                    posX += this.random.nextInt(extra) - this.random.nextInt(extra);
                    posZ += this.random.nextInt(extra) - this.random.nextInt(extra);
                }
                airfound = 0;
                inarow = 0;
            }
        }

        entity.motionX = entity.motionY = entity.motionZ = 0.0;
        if (found == 0) {
            posX = originalX;
            posZ = originalZ;
            posY = Math.min(180, this.world.getActualHeight() - 2);
            while (posY > 1 && (!this.world.isAirBlock(new BlockPos(posX, posY + 1, posZ))
                || !this.world.isAirBlock(new BlockPos(posX, posY, posZ))
                || this.world.isAirBlock(new BlockPos(posX, posY - 1, posZ)))) {
                posY--;
            }
        }
        double newX = posX < 0 ? posX - 0.5 : posX + 0.5;
        double newZ = posZ < 0 ? posZ - 0.5 : posZ + 0.5;
        entity.setLocationAndAngles(newX, posY, newZ, entity.rotationYaw, 0.0F);
        if (entity instanceof EntityPlayer) {
            bringPets((EntityPlayer) entity, oldX, oldY, oldZ, newX, posY, newZ);
        }
        return true;
    }

    /** Like the original, owned pets that are not sitting follow their owner through the teleport. */
    private void bringPets(EntityPlayer owner, double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
        if (this.oldWorld.isRemote || this.oldWorld == this.world) {
            return;
        }
        AxisAlignedBB area = new AxisAlignedBB(oldX - 24.0, oldY - 12.0, oldZ - 24.0, oldX + 24.0, oldY + 12.0, oldZ + 24.0);
        float yaw = owner.rotationYaw;
        MinecraftServer server = this.world.getMinecraftServer();
        for (EntityTameable pet : this.oldWorld.getEntitiesWithinAABB(EntityTameable.class, area)) {
            if (pet.isSitting() || !pet.isOwner(owner)) {
                continue;
            }
            // The owner is mid-transfer; move pets once the current tick has finished.
            server.addScheduledTask(() -> {
                if (!pet.isDead && pet.world == this.oldWorld) {
                    pet.changeDimension(this.newdim, (world, moved, movedYaw) -> {
                        moved.setLocationAndAngles(newX, newY, newZ, yaw, 0.0F);
                        moved.motionX = moved.motionY = moved.motionZ = 0.0;
                    });
                }
            });
        }
    }
}
