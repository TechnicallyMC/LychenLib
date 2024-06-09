package net.technically.lychenlib.utils;

import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class Detonation {
    public enum DetonationType {
        NORMAL,
        BLOCKS,
        BLOCKS_ONLY
    }
    protected World world;
    protected float detonationStrength;

    public Detonation(World world, float detonationStrength) {
        this.world = world;
        this.detonationStrength = detonationStrength;
    }

    public void detonate(DetonationType type, double x, double y, double z) {
        if (world == null || detonationStrength <= 0) {
            System.err.println("Detonation failed: Invalid world or detonation strength.");
            return;
        }

        Random random = new Random();
        int radius = (int) (detonationStrength / 2);

        if (type == DetonationType.NORMAL) {
            world.createExplosion(null, x, y, z, detonationStrength, World.ExplosionSourceType.BLOCK);
        } else if (type == DetonationType.BLOCKS_ONLY || type == DetonationType.BLOCKS) {
            for (int i = -radius; i <= radius; i++) {
                for (int j = -radius; j <= radius; j++) {
                    for (int k = -radius; k <= radius; k++) {
                        BlockPos pos = new BlockPos((int) (x + i), (int) (y + j), (int) (z + k));
                        if (world.getBlockState(pos).isAir()) continue;
                        FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, world.getBlockState(pos));
                        double velocityX = random.nextGaussian() * 1.2;
                        double velocityY = random.nextGaussian() * 1.2;
                        double velocityZ = random.nextGaussian() * 1.2;
                        fallingBlockEntity.setNoGravity(true);
                        fallingBlockEntity.setVelocity(velocityX, velocityY, velocityZ);
                        world.spawnEntity(fallingBlockEntity);
                        fallingBlockEntity.setNoGravity(false);
                    }
                }
            }

            if (type == DetonationType.BLOCKS) {
                world.createExplosion(null, x, y, z, detonationStrength, World.ExplosionSourceType.BLOCK);
            }
        }
    }
}
