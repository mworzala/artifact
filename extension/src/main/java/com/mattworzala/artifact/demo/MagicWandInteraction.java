package com.mattworzala.artifact.demo;

import com.mattworzala.artifact.api.item.Item;
import com.mattworzala.artifact.api.item.type.Interactable;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.type.monster.EntityZombie;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Direction;

public class MagicWandInteraction implements Interactable {
    @Override
    public void onUseInAir(Player player, Item item, ItemStack itemStack, Player.Hand hand) {
        System.out.println("USE IN AIR");
    }

    @Override
    public void onUseOnEntity(Player player, Item item, ItemStack itemStack, Player.Hand hand, Entity entity) {
        System.out.println("USE ON ENTITY " + itemStack + " " + hand + " " + entity);
    }

    @Override
    public void onUseOnBlock(Player player, Item item, ItemStack itemStack, Player.Hand hand, BlockPosition position, Direction blockFace) {
        System.out.println("USE ON BLOCK");
        EntityZombie zombie = new EntityZombie(position.toPosition());
        zombie.setInstance(player.getInstance());
    }
}
