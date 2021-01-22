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
        ItemStack itemStack = new ArtifactItemStackImpl(this, getItem(), (byte) amount);
        //todo could cache the json message versions of the components to avoid serialization
        itemStack.setDisplayName(MinestomComponentSerializer.get()
                .serialize(AdventureUtil.defaultNoItalic(getTitle())));
        itemStack.setLore(getDescription().stream()
                //todo better approach to defaultNoItalic?
                .map(AdventureUtil::defaultNoItalic)
                .map(MinestomComponentSerializer.get()::serialize)
                .collect(Collectors.toList()));
        itemStack.setCustomModelData(getNumericId());
        itemStack.setUnbreakable(true);
        itemStack.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        Data data = new DataImpl();
        // Apply type data
        getTypes().stream()
                .sorted((a, b) -> -a.compareTo(b))
                .forEach(it -> it.applyData(data));
        // Apply artifact data
        // todo could probably cache the tag list
        data.set(Item.ID_KEY, getId().toString(), String.class);
        data.set(Item.TAG_KEY, getTags().stream()
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
