package com.mattworzala.artifact.extension.mixin;

import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class MixinPlayer {

    @Inject(method = "openInventory", at = @At("HEAD"), require = 1, remap = false)
    private void openInventory(Inventory inventory, CallbackInfoReturnable<Boolean> cir) {
        System.out.println("Hello, World");
    }
}
