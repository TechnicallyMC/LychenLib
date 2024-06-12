package net.technically.lychenlib.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityUtil {
    public static <T extends Entity> T spawnEntity(World world, EntityType<T> entityType, BlockPos pos) {
        T entity = entityType.create(world);
        if (entity != null) {
            entity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.random.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(entity);
        }
        return entity;
    }

    public static void applyEffect(Entity entity, StatusEffectInstance effect) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).addStatusEffect(effect);
        }
    }

    public static void clearAllEffects(Entity entity) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).clearStatusEffects();
        }
    }
}
