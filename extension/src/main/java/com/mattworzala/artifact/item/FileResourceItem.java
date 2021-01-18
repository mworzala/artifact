package com.mattworzala.artifact.item;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mattworzala.artifact.api.item.Item;
import com.mattworzala.artifact.api.item.ItemTag;
import com.mattworzala.artifact.api.item.ItemType;
import com.mattworzala.artifact.json.ItemTagAdapters;
import com.mattworzala.artifact.json.ItemTypeAdapters;
import com.mattworzala.artifact.json.extra.MiniMessageAdapters;
import com.mattworzala.resource.Identifier;
import com.mattworzala.resource.json.process.PostProcessed;
import com.mattworzala.resource.json.process.Required;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class FileResourceItem implements ItemBase, PostProcessed {
    @Required
    private Identifier id;
    @SerializedName("extends")
    private Item parent;
    private Material item;
    @JsonAdapter(ItemTagAdapters.Map.class)
    private Map<Identifier, ItemTag> tags;
    @JsonAdapter(ItemTypeAdapters.Map.class)
    private Map<Identifier, ItemType> types;
    /* Pseudo required, but cannot be determined without `parent`. It is validated in post process. */
    private Component title;
    @JsonAdapter(MiniMessageAdapters.List.class)
    private List<Component> description;

    private FileResourceItem() {}

    @Override
    public void postProcess() {

    }

    @Override
    public @NotNull Identifier getId() {
        return id;
    }

    @Override
    public @NotNull Material getItem() {
        Material item = this.item;
        if (item == null && this.parent != null)
            item = this.parent.getItem();

        // Validated during post process, this is just a formality.
        if (item == null) throw new IllegalStateException("Item material is null, this should not happen!");
        return item;
    }

    @Override
    public boolean hasTag(@NotNull Identifier id) {
        return this.tags.containsKey(id);
    }

    @Override
    public @NotNull Set<ItemTag> getTags() {
        Set<ItemTag> tags = Set.copyOf(this.tags.values());
        if (tags == null && this.parent != null)
            tags = this.parent.getTags();
        return tags == null ? Collections.emptySet() : Collections.unmodifiableSet(tags);
    }

    @Override
    public <T extends ItemType> @Nullable T getType(@NotNull Identifier id) {
        //noinspection unchecked
        return (T) this.tags.get(id);
    }

    @Override
    public @NotNull Set<ItemType> getTypes() {
        Set<ItemType> types = Set.copyOf(this.types.values());
        if (types == null && this.parent != null)
            types = this.parent.getTypes();
        return types == null ? Collections.emptySet() : Collections.unmodifiableSet(types);
    }

    @Override
    public @NotNull Component getTitle() {
        Component title = this.title;
        if (title == null && this.parent != null)
            title = this.parent.getTitle();

        // Validated during post process, this is just a formality.
        if (title == null) throw new IllegalStateException("Title is null, this should not happen!");
        return title;
    }

    @Override
    public @NotNull List<Component> getDescription() {
        List<Component> description = this.description;
        if (description == null && this.parent != null)
            description = this.parent.getDescription();
        return description == null ? Collections.emptyList() : Collections.unmodifiableList(description);
    }
}
