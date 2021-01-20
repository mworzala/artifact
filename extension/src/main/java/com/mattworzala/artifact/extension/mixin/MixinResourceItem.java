package com.mattworzala.artifact.extension.mixin;

import com.mattworzala.artifact.item.FileResourceItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FileResourceItem.class)
public class MixinResourceItem {
    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        System.out.println("FWAFAWFAWFAWFAWFWAFAWFAWFAWF");
    }
}
