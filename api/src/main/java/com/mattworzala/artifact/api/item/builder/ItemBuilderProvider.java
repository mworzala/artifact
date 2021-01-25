package com.mattworzala.artifact.api.item.builder;

import com.mattworzala.resource.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.ServiceLoader;

public interface ItemBuilderProvider {
    ServiceLoader<ItemBuilderProvider> LOADER = ServiceLoader.load(ItemBuilderProvider.class);

    static ItemBuilderProvider first() {
        Optional<ItemBuilderProvider> provider = ItemBuilderProvider.LOADER.findFirst();
        if (provider.isEmpty())
            throw new RuntimeException("No ItemBuilder provider could be found!");
        return provider.get();
    }

    @NotNull
    ItemBuilder create(Identifier id);
}
