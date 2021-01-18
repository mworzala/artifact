package com.mattworzala.artifact.item;

import com.mattworzala.artifact.api.item.Item;
import com.mattworzala.artifact.api.item.ItemTag;
import com.mattworzala.resource.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadSafeItemTag implements ItemTag {
    private final Identifier id;
    private final Map<Identifier, Item> items = new ConcurrentHashMap<>();

    public ThreadSafeItemTag(Identifier id) {
        this.id = id;
    }

    @Override
    public @NotNull Identifier getId() {
        return id;
    }

    @Override
    public boolean hasItem(@NotNull Identifier id) {
        return this.items.containsKey(id);
    }

    @Override
    public @NotNull Set<Item> getItems() {
        return Set.copyOf(this.items.values());
    }

    @Override
    public void addItem(@NotNull Item item) {
        this.items.put(item.getId(), item);
    }
}
