package net.technically.lychenlib.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtil {
    public static boolean isSolidBlock(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isSolidBlock(world, pos);
    }

    public static BlockEntity getBlockEntity(World world, BlockPos pos) {
        return world.getBlockEntity(pos);
    }

    public static boolean isBlockInteractable(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.hasBlockEntity() || state.hasRandomTicks();
    }
}
