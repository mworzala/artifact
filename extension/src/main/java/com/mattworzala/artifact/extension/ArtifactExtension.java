package com.mattworzala.artifact.extension;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mattworzala.artifact.api.item.Item;
import com.mattworzala.artifact.api.item.ItemTag;
import com.mattworzala.artifact.api.item.ItemType;
import com.mattworzala.artifact.item.FileResourceItem;
import com.mattworzala.artifact.json.ItemTagAdapters;
import com.mattworzala.artifact.json.ItemTypeAdapters;
import com.mattworzala.artifact.json.extra.IdentifierAdapter;
import com.mattworzala.artifact.json.extra.MinestomAdapters;
import com.mattworzala.artifact.json.extra.MiniMessageAdapters;
import com.mattworzala.resource.Identifier;
import com.mattworzala.resource.json.process.PostProcessor;
import com.mattworzala.resource.loader.FileResourceLoader;
import com.mattworzala.resource.loader.RegistryFileResourceLoader;
import com.mattworzala.resource.registry.MutableRegistry;
import com.mattworzala.resource.registry.Registry;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.extensions.Extension;
import net.minestom.server.item.Material;

import java.nio.file.Paths;

public class ArtifactExtension extends Extension {
    @Override
    public void initialize() {
        getLogger().info("Artifact is loading!");


        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new PostProcessor())
                .registerTypeAdapter(Identifier.class, IdentifierAdapter.DESERIALIZER)
                .registerTypeAdapter(Material.class, MinestomAdapters.MATERIAL)
                .registerTypeAdapter(Component.class, MiniMessageAdapters.COMPONENT)
                .registerTypeAdapter(ItemTag.class, ItemTagAdapters.ITEM_TAG)
                .registerTypeAdapter(ItemType.class, ItemTypeAdapters.ITEM_TYPE)
                .create();

        FileResourceLoader<Item> loader = new RegistryFileResourceLoader<>(gson, (MutableRegistry<Item>) Item.REGISTRY, FileResourceItem.class);
        int loaded = loader.load(Paths.get("./extensions/test_items"));
        getLogger().info("Loaded {} items.", loaded);

        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerSpawnEvent.class, event -> {
            Player player = event.getPlayer();

            Item.REGISTRY.stream().forEach(item -> player.getInventory().addItemStack(item.create(10)));
        });
    }

    @Override
    public void terminate() {
        getLogger().info("Artifact is being disabled!");
    }
}
