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
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getPlayer().getWorld().isClient) {
            ScreenshakeInstance screenshake = new ScreenshakeInstance(80);
            new Detonation(context.getPlayer().getWorld(), 10f).detonate(Detonation.DetonationType.BLOCKS, context.getPlayer().getX(),context.getPlayer().getY(),context.getPlayer().getZ());
            screenshake.setIntensity(5f);
            ScreenshakeHandler.addScreenshake(screenshake);
            context.getPlayer().getWorld().playSound(null, context.getPlayer().getX(), context.getPlayer().getY(), context.getPlayer().getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }

        return super.useOnBlock(context);
    }
}
