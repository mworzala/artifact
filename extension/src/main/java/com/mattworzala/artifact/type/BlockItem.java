package com.mattworzala.artifact.type;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mattworzala.artifact.api.item.ArtifactItemStack;
import com.mattworzala.artifact.api.item.Item;
import com.mattworzala.artifact.api.item.ItemType;
import com.mattworzala.resource.Identifier;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.CustomBlock;
import net.minestom.server.item.ItemStack;
import net.minestom.server.registry.Registries;
import org.jetbrains.annotations.NotNull;

//todo item compat, drop the item when broken
public class BlockItem implements ItemType {
    public static final Identifier TYPE_ID = Identifier.of("artifact", "block_item");
    public static final ItemType.Factory<BlockItem> FACTORY = new Factory();

    private final CustomBlock block;
    private final short displayId;

    public BlockItem(CustomBlock block) {
        this(block, (short) -1);
    }

    public BlockItem(CustomBlock block, short displayId) {
        this.block = block;
        this.displayId = displayId;
    }

    @Override
    public @NotNull Identifier getId() {
        return TYPE_ID;
    }

    public @NotNull CustomBlock getBlock() {
        return block;
    }

    public short getDisplayId() {
        return displayId == -1 ? getBlock().getDefaultBlockStateId() : displayId;
    }

    //todo maybe find a new home for the event handler, i dont love the static block or event handler kinda hidden here.
    static {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerBlockPlaceEvent.class, BlockItem::handleBlockPlace);
    }

    /**
     * Handles placement of custom blocks.
     */
    private static void handleBlockPlace(PlayerBlockPlaceEvent event) {
        ItemStack itemStack = event.getPlayer().getItemInHand(event.getHand());

        ArtifactItemStack artifactStack = ArtifactItemStack.of(itemStack);
        if (artifactStack == null) return;
        Item item = artifactStack.getItem();

        BlockItem blockItem = item.getType(TYPE_ID);
        if (blockItem == null)
            return;

        event.setCustomBlock(blockItem.getBlock());
        event.setBlockStateId(blockItem.getDisplayId());
    }

    /**
     * Represented as follows in JSON
     * <pre>
     * {
     *   "id": "artifact:block_item",       The id of this item type
     *   "block": "artifact:block_item",    The custom block id to place
     *   "display?": "minecraft:stone",     The display block (optional)
     *   "item_compat?": true               If set to true, items can be used as blocks (optional)
     * }
     * </pre>
     */
    private static class Factory extends ItemType.Factory<BlockItem> {
        public Factory() {
            super(TYPE_ID);
        }

        @Override
        public @NotNull BlockItem create(@NotNull JsonObject rawType) {
            assertPresent(rawType, "block");
            String customBlockId = rawType.get("block").getAsString();
            CustomBlock customBlock = MinecraftServer.getBlockManager().getCustomBlock(customBlockId);
            if (customBlock == null)
                throw new JsonParseException("Failed to locate custom block '" + customBlockId + "', please ensure it was registered at an appropriate time.");

            short displayId = -1;
            if (rawType.has("display")) {
                String displayBlockId = rawType.get("display").getAsString();
                Block displayBlock = Registries.getBlock(displayBlockId);
                if (displayBlock == Block.AIR)
                    throw new JsonParseException("Failed to locate display block '" + displayBlockId + "'.");
                displayId = displayBlock.getBlockId();
            }

            return new BlockItem(customBlock, displayId);
        }
    }
}
