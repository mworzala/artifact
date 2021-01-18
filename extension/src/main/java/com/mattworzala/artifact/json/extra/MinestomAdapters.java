package com.mattworzala.artifact.json.extra;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minestom.server.item.Material;
import net.minestom.server.registry.Registries;

import java.lang.reflect.Type;

public interface MinestomAdapters {
    JsonDeserializer<Material> MATERIAL = new SingleMaterial();

    class SingleMaterial implements JsonDeserializer<Material> {
        @Override
        public Material deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String namespacedId = context.deserialize(json, String.class);
            Material match = Registries.getMaterial(namespacedId);
            if (match == Material.AIR && !namespacedId.equals("minecraft:air"))
                throw new JsonParseException("Not a Material: " + json);
            return match;
        }
    }
}
