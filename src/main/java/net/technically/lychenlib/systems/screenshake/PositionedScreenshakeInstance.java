package net.technically.lychenlib.systems.screenshake;

import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;

public class PositionedScreenshakeInstance extends ScreenshakeInstance {
    private Vec3d position;
    private float falloffDistance;
    private float maxDistance;
    private float minDot;

    public PositionedScreenshakeInstance(int duration, Vec3d position, float falloffDistance, float minDot, float maxDistance) {
        super(duration);
        this.position = position;
        this.falloffDistance = falloffDistance;
        this.minDot = minDot;
        this.maxDistance = maxDistance;
    }

    @Override
    public float updateIntensity(Camera camera) {
        float intensity = super.updateIntensity(camera);
        float distance = (float) position.distanceTo(camera.getPos());
        if (distance > maxDistance) {
            return 0;
        }
        float distanceMultiplier = 1;
        if (distance > falloffDistance) {
            float remaining = maxDistance - falloffDistance;
            float current = distance - falloffDistance;
            distanceMultiplier = 1 - current / remaining;
        }

        Vec3d lookDirection = getCameraLookDirection(camera);
        Vec3d directionToScreenshake = position.subtract(camera.getPos()).normalize();
        float angle = (float) Math.max(minDot, lookDirection.dotProduct(directionToScreenshake));
        return intensity * distanceMultiplier * angle;
    }

    private Vec3d getCameraLookDirection(Camera camera) {
        double pitch = Math.toRadians(camera.getPitch());
        double yaw = Math.toRadians(camera.getYaw());

        double x = -Math.cos(pitch) * Math.sin(yaw);
        double y = -Math.sin(pitch);
        double z = Math.cos(pitch) * Math.cos(yaw);

        return new Vec3d(x, y, z);
    }
}
