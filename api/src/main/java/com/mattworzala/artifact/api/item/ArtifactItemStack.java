package com.mattworzala.artifact.api.item;

import com.mattworzala.resource.Identifier;
import com.mattworzala.resource.registry.Registry;
import net.minestom.server.data.Data;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public interface ArtifactItemStack {

    @Nullable
    static ArtifactItemStack of(ItemStack itemStack) {
        if (itemStack instanceof ArtifactItemStack)
            return (ArtifactItemStack) itemStack;
        String id = getId(itemStack);
        if (id == null) return null;
        Item item = Registry.get(Item.REGISTRY, id);
        if (item == null) return null;
        return () -> item;
    }

    @Nullable
    static String getId(ItemStack itemStack) {
        Data data = itemStack.getData();
        if (data == null || !data.hasKey(Item.ID_KEY)) return null;
        return data.get(Item.ID_KEY);
    }

    @Nullable
    static Stream<Identifier> getTags(ItemStack itemStack) {
        Data data = itemStack.getData();
        if (data == null || !data.hasKey(Item.TAG_KEY)) return null;
        String tagString = Objects.requireNonNull(data.get(Item.TAG_KEY));
        return Arrays.stream(tagString.split(",")).map(Identifier::of);
    }

    @NotNull
    Item getItem();
}
