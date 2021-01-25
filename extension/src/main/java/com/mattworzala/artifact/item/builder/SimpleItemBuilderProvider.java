package com.mattworzala.artifact.item.builder;

import com.mattworzala.artifact.api.item.builder.ItemBuilder;
import com.mattworzala.artifact.api.item.builder.ItemBuilderProvider;
import com.mattworzala.resource.Identifier;
import org.jetbrains.annotations.NotNull;

public class SimpleItemBuilderProvider implements ItemBuilderProvider {
    @Override
    public @NotNull ItemBuilder create(Identifier id) {
        return null;
    }


}
