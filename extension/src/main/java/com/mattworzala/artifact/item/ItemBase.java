package com.mattworzala.artifact.item;

import com.mattworzala.artifact.api.item.Item;
import com.mattworzala.artifact.util.AdventureUtil;
import net.kyori.adventure.platform.minestom.MinestomComponentSerializer;
import net.kyori.adventure.text.Component;
import net.minestom.server.data.Data;
import net.minestom.server.data.DataImpl;
import net.minestom.server.item.ItemFlag;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public interface ItemBase extends Item {
    @Override
    default int getNumericId() {
        return getId().hashCode();
    }

    @Override
    default @NotNull ItemStack create(int amount) {
        //todo ArtifactItemStack
        ItemStack itemStack = new ItemStack(getItem(), (byte) amount);
        itemStack.setDisplayName(MinestomComponentSerializer.get()
                .serialize(AdventureUtil.defaultNoItalic(getTitle())));
        itemStack.setLore(getDescription().stream()
                .map(AdventureUtil::defaultNoItalic)
                .map(MinestomComponentSerializer.get()::serialize)
                .collect(Collectors.toList()));
        itemStack.setCustomModelData(getNumericId());
        itemStack.setUnbreakable(true);
        itemStack.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        Data data = new DataImpl();
        data.set("artifact:id", getId().toString(), String.class);
        data.set("artifact:tags", getTags().stream()
                .map(tag -> tag.getId().toString())
                .collect(Collectors.toList()), List.class);
        itemStack.setData(data);
        return itemStack;
    }

    @Override
    default @NotNull Component toComponent() {
        return getTitle().hoverEvent(AdventureUtil.showArtifactItem(this));
    }
}
