package net.technically.lychenlib.systems.screenshake;

import net.minecraft.client.render.Camera;
import net.minecraft.util.math.MathHelper;

public class ScreenshakeInstance {
    private int progress;
    private final int duration;
    private float intensity1, intensity2, intensity3;

    public ScreenshakeInstance(int duration) {
        this.duration = duration;
    }

    public ScreenshakeInstance setIntensity(float intensity) {
        return setIntensity(intensity, intensity);
    }

    public ScreenshakeInstance setIntensity(float intensity1, float intensity2) {
        return setIntensity(intensity1, intensity2, intensity2);
    }

    public ScreenshakeInstance setIntensity(float intensity1, float intensity2, float intensity3) {
        this.intensity1 = intensity1;
        this.intensity2 = intensity2;
        this.intensity3 = intensity3;
        return this;
    }

    public float updateIntensity(Camera camera) {
        progress++;
        float percentage = progress / (float) duration;
        if (intensity2 != intensity3) {
            if (percentage >= 0.5f) {
                return MathHelper.lerp((percentage - 0.5f) / 0.5f, intensity2, intensity1);
            } else {
                return MathHelper.lerp(percentage / 0.5f, intensity1, intensity2);
            }
        } else {
            return MathHelper.lerp(percentage, intensity1, intensity2);
        }
    }

    public boolean isComplete() {
        return progress >= duration;
    }

    public int getProgress() {
        return progress;
    }

    public int getDuration() {
        return duration;
    }
}
