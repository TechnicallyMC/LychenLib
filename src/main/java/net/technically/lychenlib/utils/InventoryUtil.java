package net.technically.lychenlib.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class InventoryUtil {
    public static boolean addItemToPlayer(PlayerEntity player, ItemStack stack) {
        if (!player.getInventory().insertStack(stack)) {
            player.dropItem(stack, false);
            return false;
        }
        return true;
    }

    public static boolean removeItemFromPlayer(PlayerEntity player, ItemStack stack) {
        findItemInInventory(player, stack).decrement(findItemInInventory(player,stack).getCount());
        return true;
    }

    public static ItemStack findItemInInventory(PlayerEntity player, ItemStack stack) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack item = player.getInventory().getStack(i);
            if (ItemStack.areItemsEqual(stack, item) && ItemStack.areEqual(stack, item)) {
                return item;
            }
        }
        return ItemStack.EMPTY;
    }
}
