package com.mattworzala.artifact.api.item.builder;

import com.mattworzala.artifact.api.item.ItemTag;
import com.mattworzala.artifact.api.item.ItemType;
import com.mattworzala.resource.Identifier;
import org.jetbrains.annotations.NotNull;

public interface ItemBuilder {
    static ItemBuilder create(@NotNull Identifier id) {
        return ItemBuilderProvider.first().create(id);
    }

    ItemBuilder tag(Identifier tag);

    ItemBuilder tag(ItemTag tag);

    ItemBuilder type(ItemType type);


}
