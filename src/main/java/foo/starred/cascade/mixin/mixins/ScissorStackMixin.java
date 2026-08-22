package foo.starred.cascade.mixin.mixins;

import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Deque;
import java.util.Objects;

//? if >= 26.2
//import foo.starred.cascade.geometry.CascadeScreenRectangle;

@Mixin(targets = "net.minecraft.client.gui.GuiGraphicsExtractor$ScissorStack")
public class ScissorStackMixin {
    //? if >= 26.2 {
    /*@Shadow
    @Final
    private Deque<ScreenRectangle> stack;

    @Shadow
    @Final
    private ScreenRectangle screenSize;

    @Inject(method = "push", at = @At("HEAD"), cancellable = true)
    private void cascade$push(ScreenRectangle rectangle, CallbackInfo ci) {
        if (!(rectangle instanceof CascadeScreenRectangle)) return;

        final ScreenRectangle result = rectangle.intersection(Objects.requireNonNullElse(this.stack.peekLast(), this.screenSize));
        this.stack.addLast(result != null ? CascadeScreenRectangle.of(result) : CascadeScreenRectangle.EMPTY);
        ci.cancel();
    }
    *///? }
}