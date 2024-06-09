package net.technically.lychenlib.systems.screenshake;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.PerlinNoiseSampler;

import java.util.ArrayList;
import java.util.Random;

public class ScreenshakeHandler {
    private static final Random RANDOM = new Random();
    private static final PerlinNoiseSampler sampler = new PerlinNoiseSampler(net.minecraft.util.math.random.Random.create());

    public static final ArrayList<ScreenshakeInstance> INSTANCES = new ArrayList<>();
    public static float intensity;

    public static void clientTick(PlayerEntity player) {
        // Get the Minecraft client instance
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            // Get the camera instance from the client
            Camera camera = client.gameRenderer.getCamera();

            double sum = Math.min(INSTANCES.stream().mapToDouble(i -> i.updateIntensity(camera)).sum(), getMaxScreenshakeIntensity());
            intensity = (float) Math.pow(sum, 3);
            INSTANCES.removeIf(i -> i.getProgress() >= i.getDuration());

            // Apply screenshake to the player's view
            applyScreenshake(player);
        }
    }

    public static void addScreenshake(ScreenshakeInstance instance) {
        INSTANCES.add(instance);
    }

    public static float randomizeOffset(int offset) {
        float min = -intensity * 2;
        float max = intensity * 2;
        float sampled = (float) sampler.sample((MinecraftClient.getInstance().world.getTime() % 24000L + MinecraftClient.getInstance().getTickDelta()) / intensity, offset, 0) * 1.5f;
        return min >= max ? min : sampled * max;
    }

    private static float getMaxScreenshakeIntensity() {
        // Replace this with your logic to determine the maximum screen shake intensity
        return 1000.0f;
    }

    private static void applyScreenshake(PlayerEntity player) {
        float yawOffset = randomizeOffset(10);
        float pitchOffset = randomizeOffset(-5); // Adjust the range for pitch offset
        float newYaw = MathHelper.wrapDegrees(player.getYaw() + yawOffset);
        float newPitch = MathHelper.clamp(player.getPitch() + pitchOffset, -90, 90);
        player.setYaw(newYaw);
        player.setPitch(newPitch);
    }
}
