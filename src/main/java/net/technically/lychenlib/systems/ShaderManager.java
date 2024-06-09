package net.technically.lychenlib.systems;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.technically.lychenlib.setup.LychenParticles;

public class ShaderManager {
    public static void bootstrapper() {
        LychenParticles.init();
    }


}
