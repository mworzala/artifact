package com.mattworzala.artifact.api.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mattworzala.resource.Identifier;
import com.mattworzala.resource.Resource;
import com.mattworzala.resource.registry.MutableRegistry;
import com.mattworzala.resource.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Represents a type of an item.
 * <p>
 * Types can be used to denote specific characteristics of an item. todo this
 *
 *
 */
public interface ItemType extends Resource {
    Registry<Factory<?>> REGISTRY = MutableRegistry.newThreadSafeRegistry();

    static <T extends ItemType> Factory<T> factory(Identifier id, Function<JsonObject, T> factory) {
        return new Factory<>(id) {
            @Override
            public @NotNull T create(@NotNull JsonObject rawType) {
                return factory.apply(rawType);
            }
        };
    }

    abstract class Factory<T extends ItemType> implements Resource {
        private final Identifier id;

        public Factory(Identifier id) {
            this.id = id;
        }

        @NotNull
        public abstract T create(@NotNull JsonObject rawType);

        @Override
        public @NotNull Identifier getId() {
            return id;
        }

        protected void assertPresent(JsonObject obj, String... fields) {
            for (String field : fields) {
                if (!obj.has(field))
                    throw new JsonParseException("Missing required field '" + field + "' in " + obj);
            }
        }
    }
}
