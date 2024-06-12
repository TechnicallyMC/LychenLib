package net.technically.lychenlib;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.technically.lychenlib.item.IClickUseItem;

import java.util.HashSet;
import java.util.Set;

public class LychenClient implements ClientModInitializer {
    public static final Set<Item> RENDERABLE_ITEMS = new HashSet<>();

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(LychenLibrary.PACKET_ENTITY_TARGET, (client, handler, buf, responseSender) -> {
            ItemStack stack = buf.readItemStack();
            client.execute(() -> {client.player.sendMessage(Text.of("hi")); client.gameRenderer.showFloatingItem(stack);});
        });

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            for (Item item : RENDERABLE_ITEMS) {
                Identifier itemId = Registries.ITEM.getId(item);
                out.accept(new ModelIdentifier(itemId, "inventory"));
                out.accept(new ModelIdentifier(new Identifier(itemId.getNamespace(), itemId.getPath() + "_handheld"), "inventory"));
            }
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (!world.isClient && stack.getItem() instanceof IClickUseItem) {
                ClientPlayNetworking.send(LychenLibrary.PACKET_CLICK_USE, new PacketByteBuf(Unpooled.buffer()));
                return TypedActionResult.success(stack);
            }
            return TypedActionResult.pass(stack);
        });
    }

    public static void registerRenderableItem(Item item) {
        RENDERABLE_ITEMS.add(item);
    }
}