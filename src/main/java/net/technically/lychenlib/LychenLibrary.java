package net.technically.lychenlib;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.technically.lychenlib.item.IClickUseItem;
import net.technically.lychenlib.utils.LychenRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LychenLibrary implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("LychenLib");
	public static final String MODID = "lychen";
	// Network Packet Identifiers
	public static final Identifier PACKET_CLICK_USE = id("click_use");
	public static final Identifier PACKET_ENTITY_TARGET = id("entity_target");

	// Item Tags
	public static final TagKey<Item> IMMUNE_ITEMS = TagKey.of(Registries.ITEM.getKey(), id("immune_items"));
	public static final TagKey<Item> ADVANCED_TOOLS = TagKey.of(Registries.ITEM.getKey(), id("advanced_tools"));
	// Game Rules
	public static final GameRules.Key<GameRules.BooleanRule> ENCHANTED_SMELTING = GameRuleRegistry.register("lychen:enchanted_smelt", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));

	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(PACKET_ENTITY_TARGET, (server, player, handler, buf, responseSender) -> {
			server.execute(() -> {});
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (!world.isClient && entity instanceof LivingEntity) {
				PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
				packet.writeInt(entity.getId());
				ServerPlayNetworking.send((ServerPlayerEntity) player, PACKET_ENTITY_TARGET, packet);
				return ActionResult.PASS;
			}
			return ActionResult.PASS;
		});

		ServerPlayNetworking.registerGlobalReceiver(PACKET_CLICK_USE, (minecraftServer, serverPlayer, serverPlayNetworkHandler, packetByteBuf, packetSender) -> minecraftServer.execute(() -> {
			if (serverPlayer.getMainHandStack().getItem() instanceof IClickUseItem item) {
				item.lychen$doAttack(serverPlayer);
			}
		}));
	}

	public static ItemStack applyEnchantments(ItemStack stack, EnchantmentLevelEntry... entries) {
		for (EnchantmentLevelEntry entry : entries) {
			stack.addEnchantment(entry.enchantment, entry.level);
		}
		return stack;
	}

	public static ItemStack createEnchantedBook(EnchantmentLevelEntry... entries) {
		ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
		for (EnchantmentLevelEntry entry : entries) {
			EnchantedBookItem.addEnchantment(stack, entry);
		}
		return stack;
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}