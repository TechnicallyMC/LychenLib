package net.technically.lychenlib.utils;

public class LychenMath {
    public static int loopClamp(int value, int min, int max) {
        if (min == max) return min;
        if (max < min) {
            int temp = min;
            min = max;
            max = temp;
        }
        if (value < min) {
            return max - ((min - value) % (max - min));
        }
        return min + ((value - min) % (max - min));
    }

    public static float loopClamp(float value, float min, float max) {
        if (min == max) return min;
        if (max < min) {
            float temp = min;
            min = max;
            max = temp;
        }
        if (value < min) {
            return max - ((min - value) % (max - min));
        }
        return min + ((value - min) % (max - min));
    }

    public static double loopClamp(double value, double min, double max) {
        if (min == max) return min;
        if (max < min) {
            double temp = min;
            min = max;
            max = temp;
        }
        if (value < min) {
            return max - ((min - value) % (max - min));
        }
        return min + ((value - min) % (max - min));
    }
}
