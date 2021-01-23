package com.mattworzala.artifact.api.item.builder;

import com.mattworzala.artifact.api.item.ItemTag;
import com.mattworzala.artifact.api.item.ItemType;
import com.mattworzala.resource.Identifier;

public interface ItemBuilder {


    ItemBuilder tag(Identifier tag);

    ItemBuilder tag(ItemTag tag);

    ItemBuilder type(ItemType type);
}
