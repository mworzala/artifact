package com.mattworzala.artifact.api;

import com.mattworzala.resource.Identifier;
import com.mattworzala.resource.Resource;
import com.mattworzala.resource.registry.MutableRegistry;
import com.mattworzala.resource.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Stream;

/**
 * Represents a "group" of items under a given "tag" (identifier).
 */
public interface ItemTag extends Resource {
    Registry<ItemTag> REGISTRY = MutableRegistry.newThreadSafeRegistry();

    /**
     * Checks if a given item is a member of this tag.
     *
     * @param item The item to check
     * @return True if the item is a member of this tag
     *
     * @see Item#hasTag(ItemTag)
     */
    default boolean hasItem(@NotNull Item item) {
        return hasItem(item.getId());
    }

    /**
     * Checks if the given item is a member of this tag.
     * <p>
     * No checks will be made to confirm if the given id corresponds to a loaded item,
     * it will silently return false.
     *
     * @param id The item to check
     * @return True if the item is a member of this tag
     *
     * @see Item#hasTag(Identifier)
     */
    boolean hasItem(@NotNull Identifier id);

    /**
     * Gets an immutable set of all of the items currently in this tag.
     * <p>
     * It is safe to assume that no items will be removed from the tag, however
     * it is not safe to assume that no more items will be added in the future.
     *
     * @return An immutable set of the items currently in this tag.
     */
    @NotNull
    Set<Item> getItems();

    /**
     * Gets a stream of all of the items currently in this tag.
     * <p>
     * It is safe to assume that no items will be removed from the tag, however
     * it is not safe to assume that no more items will be added in the future.
     *
     * @return A stream of all items currently in this tag.
     */
    @NotNull
    default Stream<Item> items() {
        return getItems().stream();
    }

    /**
     * Adds an item to this tag, but does not add this tag to the item.
     * <p>
     * This function should not be called manually. To add an item to a tag,
     * use the relevant builder methods or json resource option.
     *
     * todo add builder method @see here.
     *
     * @param item The item to add to the tag
     */
    void addItem(@NotNull Item item);
}
