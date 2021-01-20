package com.mattworzala.artifact.item;

import com.mattworzala.artifact.api.item.ArtifactItemStack;
import com.mattworzala.artifact.api.item.Item;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

public class ArtifactItemStackImpl extends ItemStack implements ArtifactItemStack {
    private final Item item;

    public ArtifactItemStackImpl(@NotNull Item item, @NotNull Material material, byte amount) {
        super(material, amount);
        this.item = item;
    }

    @Override
    public @NotNull Item getItem() {
        return this.item;
    }
}
