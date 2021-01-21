package com.mattworzala.artifact.api.item.type;

import com.mattworzala.artifact.api.item.Item;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Direction;

public interface Interactable {
    default void onUseInAir(Player player, Item item, ItemStack itemStack, Player.Hand hand) {}

    default void onUseOnEntity(Player player, Item item, ItemStack itemStack, Player.Hand hand, Entity entity) {}

    default void onUseOnBlock(Player player, Item item, ItemStack itemStack, Player.Hand hand, BlockPosition position, Direction blockFace) {}
}
