package net.technically.lychenlib.utils;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenerationUtil {
    public static void generateCube(World world, BlockPos start, int size, Block block) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    BlockPos pos = start.add(x, y, z);
                    world.setBlockState(pos, block.getDefaultState());
                }
            }
        }
    }

    public static BlockPos getRandomPosWithinRadius(BlockPos center, int radius) {
        Random random = new Random();
        int offsetX = random.nextInt(radius * 2 + 1) - radius;
        int offsetY = random.nextInt(radius * 2 + 1) - radius;
        int offsetZ = random.nextInt(radius * 2 + 1) - radius;
        return center.add(offsetX, offsetY, offsetZ);
    }
}
