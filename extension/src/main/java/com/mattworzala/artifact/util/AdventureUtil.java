package com.mattworzala.artifact.util;

import com.mattworzala.artifact.api.item.Item;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface AdventureUtil {

    static Component defaultNoItalic(Component c) {
        return Component.text("").decoration(TextDecoration.ITALIC, false).append(c);
    }

    @NotNull
    static HoverEvent<HoverEvent.ShowItem> showArtifactItem(Item item) {
        return showMinestomItem(item.create());
    }

    @NotNull
    static HoverEvent<HoverEvent.ShowItem> showMinestomItem(ItemStack item) {
        String tag = item.toNBT().getCompound("tag").toSNBT();
        return HoverEvent.showItem(Key.key(item.getMaterial().getName()), 1, BinaryTagHolder.of(tag));
    }
}
