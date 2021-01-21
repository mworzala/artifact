package com.mattworzala.artifact.api.item;

import com.mattworzala.resource.Identifier;
import com.mattworzala.resource.Resource;
import com.mattworzala.resource.registry.MutableRegistry;
import com.mattworzala.resource.registry.Registry;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public interface Item extends Resource {
    Registry<Item> REGISTRY = MutableRegistry.newThreadSafeRegistry();
    String ID_KEY = "artifact:id";
    String TAG_KEY = "artifact:tags";

    /**
     * Gets the id of this item.
     * <p>
     * This value should not be changed, see {@link #getNumericId()} for more information.
     *
     * @return The id of this item
     *
     * @see #getNumericId()
     */
    @Override
    @NotNull Identifier getId();

    /**
     * Gets the {@link Material} to use for this item.
     *
     * @return The material of this item
     */
    @NotNull Material getItem();

    // Tags

    default boolean hasTag(@NotNull ItemTag tag) {
        return hasTag(tag.getId());
    }

    boolean hasTag(@NotNull Identifier id);

    @NotNull Set<ItemTag> getTags();

    // Types

    <T extends ItemType> @Nullable T getType(@NotNull Identifier id);

    @NotNull Set<ItemType> getTypes();

    // Metadata

    @NotNull Component getTitle();

    @NotNull List<Component> getDescription();

    /**
     * Returns a unique numeric representation of this item. This is used for CustomModelData
     * on created {@link ItemStack}s. This value should be unique not change if resource packs
     * are being used.
     * <p>
     * The default implementation of this value can be found below. The {@link #getId()} of the
     * item should not change if possible.
     * <pre>
     * public int getNumericId() {
     *     return getId().hashCode();
     * }
     * </pre>
     *
     * @return A unique numeric representation of this item
     *
     * @see Item#getId()
     * @see Item#create()
     */
    int getNumericId();

    /**
     * Creates an {@link ItemStack} of this item, with appropriate data attached
     * and a stack size of 1.
     *
     * @return An {@link ItemStack} of this item
     */
    @NotNull @Contract(pure = true)
    default ItemStack create() { return create(1); }

    /**
     * Creates an {@link ItemStack} of this item, with appropriate data attached.
     *
     * @param amount The count of the created stack
     * @return An {@link ItemStack} of this item
     */
    @NotNull @Contract(pure = true)
    ItemStack create(int amount);

    /**
     * Creates a {@link Component} of the title, with a hover event to show the item.
     *
     * @return A chat-renderable form of the item.
     */
    @NotNull @Contract(pure = true)
    Component toComponent();
}
