package net.technically.lychenlib;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.technically.lychenlib.systems.screenshake.ScreenshakeHandler;
import net.technically.lychenlib.systems.screenshake.ScreenshakeInstance;
import net.technically.lychenlib.utils.Detonation;

public class TestItem extends Item {
    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient) {
            // Create a screenshake instance and add it to the handler
            ScreenshakeInstance screenshake = new ScreenshakeInstance(80);
            new Detonation(world, 10f).detonate(Detonation.DetonationType.BLOCKS, user.getX(),user.getY(),user.getZ());
            screenshake.setIntensity(5f); // Example intensity
            ScreenshakeHandler.addScreenshake(screenshake);

            // Play a sound effect to indicate the item has been used
            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }

        return TypedActionResult.success(this.getDefaultStack());
    }
}
