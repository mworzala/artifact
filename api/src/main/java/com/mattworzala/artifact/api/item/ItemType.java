package com.mattworzala.artifact.api.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mattworzala.resource.Identifier;
import com.mattworzala.resource.Resource;
import com.mattworzala.resource.registry.MutableRegistry;
import com.mattworzala.resource.registry.Registry;
import net.kyori.adventure.text.Component;
import net.minestom.server.data.Data;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Represents a type of an item.
 * <p>
 * Types can be used to denote specific characteristics of an item such
 * as lore and additional data.
 */
public interface ItemType extends Resource, Comparable<ItemType> {
    Registry<Factory<?>> REGISTRY = MutableRegistry.newThreadSafeRegistry();

    /**
     * Returns the applicable lore for this type.
     * <p>
     * Higher priority means the lore will be rendered first.
     *
     * @return The lore to add to the item when rendering
     *
     * @see #getPriority()
     */
    @Contract(pure = true)
    default @NotNull List<Component> applyLore() {
        return Collections.emptyList();
    }

    /**
     * Applies data to the data container of the item when creating it.
     * <p>
     * Higher priority means the data will be applied last. It may overwrite
     * values written before it, so keys should be namespaced to the application.
     * <p>
     * The keys `artifact:id` and `artifact:tags` will be overwritten if set, no
     * matter the priority.
     *
     * @param data The data container for the item.
     *
     * @see #getPriority()
     */
    @Contract(pure = true)
    default void applyData(@NotNull Data data) { }

    /**
     * Gets the priority of this type. The definition of "higher" depends on the use
     * case, see related methods for specifics.
     * <p>
     * The order of types with the same priority is undefined.
     * <p>
     * Defaults to 0.
     *
     * @return The priority of this ItemType
     */
    default int getPriority() { return 0; }

    @Override
    default int compareTo(@NotNull ItemType o) {
        return Integer.compare(getPriority(), o.getPriority());
    }


    /**
     * todo
     *
     * @param id
     * @param factory
     * @param <T>
     * @return
     */
    static <T extends ItemType> Factory<T> factory(Identifier id, Function<JsonObject, T> factory) {
        return new Factory<>(id) {
            @Override
            public @NotNull T create(@NotNull JsonObject rawType) {
                return factory.apply(rawType);
            }
        };
    }

    /**
     * todo
     *
     * @param <T>
     */
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
