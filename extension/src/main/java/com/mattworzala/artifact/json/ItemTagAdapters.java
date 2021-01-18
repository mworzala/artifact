package com.mattworzala.artifact.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mattworzala.artifact.api.item.ItemTag;
import com.mattworzala.artifact.item.ThreadSafeItemTag;
import com.mattworzala.resource.Identifier;
import com.mattworzala.resource.json.VarListDeserializer;
import com.mattworzala.resource.json.VarResourceMap;
import com.mattworzala.resource.registry.Registry;

import java.lang.reflect.Type;

public interface ItemTagAdapters {
    JsonDeserializer<ItemTag> ITEM_TAG = new Single();

    class Single implements JsonDeserializer<ItemTag> {
        @Override
        public ItemTag deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Identifier tagId = context.deserialize(json, Identifier.class);
            ItemTag tag = Registry.get(ItemTag.REGISTRY, tagId);

            // Register tag if its missing.
            if (tag == null) {
                tag = new ThreadSafeItemTag(tagId);
                Registry.register(ItemTag.REGISTRY, tagId, tag);
            }
            return tag;
        }
    }

    class List extends VarListDeserializer<ItemTag> {
        public List() { super(ItemTag.class); }
    }

    class Map extends VarResourceMap<ItemTag> {
        public Map() { super(ItemTag.class); }
    }
}
