package com.mattworzala.artifact.json;

import com.google.gson.*;
import com.mattworzala.artifact.api.item.ItemType;
import com.mattworzala.resource.Identifier;
import com.mattworzala.resource.json.VarListDeserializer;
import com.mattworzala.resource.json.VarResourceMap;
import com.mattworzala.resource.registry.Registry;

import java.lang.reflect.Type;

public interface ItemTypeAdapters {
    JsonDeserializer<ItemType> ITEM_TYPE = new Single();

    class Single implements JsonDeserializer<ItemType> {
        @Override
        public ItemType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject typeSpec;
            if (json.isJsonObject())
                typeSpec = json.getAsJsonObject();
            else if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
                typeSpec = new JsonObject();
                typeSpec.add("id", json);
            } else throw new JsonParseException("Not an Item Type: " + json);

            Identifier id = context.deserialize(typeSpec.get("id"), Identifier.class);
            ItemType.Factory<?> factory = Registry.get(ItemType.REGISTRY, id);
            if (factory == null)
                throw new JsonParseException("Item Type " + id + " is missing a type factory, ensure that it was loaded at an appropriate time.");
            return factory.create(typeSpec);
        }
    }

    class List extends VarListDeserializer<ItemType> {
        public List() { super(ItemType.class); }
    }

    class Map extends VarResourceMap<ItemType> {
        public Map() { super(ItemType.class); }
    }
}
