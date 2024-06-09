package net.technically.lychenlib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.technically.lychenlib.setup.LychenParticles;
import net.technically.lychenlib.systems.ShaderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LychenLibrary implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("LychenLib");
	public static final String MODID = "lychen";

	@Override
	public void onInitialize() {
		ShaderManager.bootstrapper();
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			Registry.register(Registries.ITEM, id("test"), new TestItem(new FabricItemSettings().rarity(Rarity.EPIC)));
		}
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}
