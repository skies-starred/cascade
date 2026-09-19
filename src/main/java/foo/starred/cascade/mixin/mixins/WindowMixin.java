package foo.starred.cascade.mixin.mixins;

import com.mojang.blaze3d.platform.Window;
import foo.starred.cascade.wrappers.svg.impl.CascadeSVG;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {
    @Unique
    private int cascade$last = -1;

    @Inject(method = "setGuiScale", at = @At("HEAD"))
    private void cascade$setGuiScale(int guiScale, CallbackInfo ci) {
        if (cascade$last == guiScale) return;

        cascade$last = guiScale;
        CascadeSVG.INSTANCE.remove(guiScale);
    }
}
