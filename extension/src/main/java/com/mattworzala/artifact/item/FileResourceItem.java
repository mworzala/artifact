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

import java.util.*;

//todo need to handle this all better. tags and types can be null which causes annoyance when deciding to return parent or it.
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
        Collection<ItemTag> tags = null;
        if (this.tags != null) tags = this.tags.values();
        if (tags == null && this.parent != null) tags = this.parent.getTags();
        return tags == null ? Collections.emptySet() : Set.copyOf(tags);
    }

    @Override
    public <T extends ItemType> @Nullable T getType(@NotNull Identifier id) {
        if (this.types == null) {
            if (this.parent != null)
                return this.parent.getType(id);
            return null;
        }
        return (T) this.types.get(id);
    }

    @Override
    public @NotNull Set<ItemType> getTypes() {
        Collection<ItemType> types = null;
        if (this.types != null) types = this.types.values();
        if (types == null && this.parent != null) types = this.parent.getTypes();
        return types == null ? Collections.emptySet() : Set.copyOf(types);
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
