package com.mattworzala.artifact.type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mattworzala.artifact.api.item.ArtifactItemStack;
import com.mattworzala.artifact.api.item.ItemType;
import com.mattworzala.artifact.api.item.type.Interactable;
import com.mattworzala.resource.Identifier;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public class InteractableItem implements ItemType {
    public static final Identifier TYPE_ID = Identifier.of("artifact", "interactable");
    public static final ItemType.Factory<InteractableItem> FACTORY = new Factory();

    private final Interactable handler;

    public InteractableItem(Interactable handler) {
        this.handler = handler;
    }

    @Override
    public @NotNull Identifier getId() {
        return TYPE_ID;
    }

    public Interactable getHandler() {
        return handler;
    }

    /*
     * Event Handling
     */

    static {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerUseItemEvent.class, InteractableItem::handleUseItem);
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerEntityInteractEvent.class, InteractableItem::handleUseItemOnEntity);
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerUseItemOnBlockEvent.class, InteractableItem::handleUseItemOnBlock);
    }

    private static void handleUseItem(PlayerUseItemEvent event) {

        ArtifactItemStack stack = ArtifactItemStack.of(event.getItemStack());
        if (stack == null) return;
        InteractableItem interact = stack.getItem().getType(TYPE_ID);
        if (interact == null) return;

        interact.getHandler().onUseInAir(event.getPlayer(), stack.getItem(), event.getItemStack(), event.getHand());
    }

    private static void handleUseItemOnEntity(PlayerEntityInteractEvent event) {
        ItemStack itemStack = event.getPlayer().getItemInHand(event.getHand());
        ArtifactItemStack stack = ArtifactItemStack.of(itemStack);
        if (stack == null) return;
        InteractableItem interact = stack.getItem().getType(TYPE_ID);
        if (interact == null) return;

        interact.getHandler().onUseOnEntity(event.getPlayer(), stack.getItem(), itemStack, event.getHand(), event.getTarget());
    }

    private static void handleUseItemOnBlock(PlayerUseItemOnBlockEvent event) {
        ArtifactItemStack stack = ArtifactItemStack.of(event.getItemStack());
        if (stack == null) return;
        InteractableItem interact = stack.getItem().getType(TYPE_ID);
        if (interact == null) return;

        interact.getHandler().onUseOnBlock(event.getPlayer(), stack.getItem(), event.getItemStack(), event.getHand(), event.getPosition(), event.getBlockFace());
    }


    private static class Factory extends ItemType.Factory<InteractableItem> {
        public Factory() { super(TYPE_ID); }

        @Override
        public @NotNull InteractableItem create(@NotNull JsonObject rawType) {
            assertPresent(rawType, "handler");
            String handlerClassName = rawType.get("handler").getAsString();

            // Load & Instantiate class
            try {
                Class<Interactable> handlerClass = (Class<Interactable>) Class.forName(handlerClassName);
                Interactable handler = handlerClass.getDeclaredConstructor().newInstance();
                return new InteractableItem(handler);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Unable to locate item interaction handler class " + handlerClassName);
            } catch (Exception e) {
                throw new JsonParseException(e);
            }
        }
    }
}
