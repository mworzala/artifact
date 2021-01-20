package com.mattworzala.artifact.demo;

import net.minestom.server.data.Data;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.CustomBlock;
import net.minestom.server.utils.BlockPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockDemo extends CustomBlock {
    public BlockDemo() {
        super(Block.COMMAND_BLOCK, "artifact:demo_block");
    }

    @Override
    public void onPlace(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        System.out.println("PLACE DEMO BLOCK");
    }

    @Override
    public void onDestroy(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        System.out.println("DESTROY DEMO BLOCK");
    }

    @Override
    public boolean onInteract(@NotNull Player player, @NotNull Player.Hand hand, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        System.out.println("INTERACT WITH DEMO BLOCK");
        return false;
    }

    @Override
    public short getCustomBlockId() {
        return 12542;
    }
}
