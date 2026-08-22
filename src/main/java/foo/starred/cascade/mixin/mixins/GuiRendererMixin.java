package foo.starred.cascade.mixin.mixins;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderPass;
import foo.starred.cascade.Cascade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.GuiRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >= 26.2
//import foo.starred.cascade.geometry.CascadeScreenRectangle;

@Mixin(GuiRenderer.class)
public class GuiRendererMixin {
    //? if >= 26.2 {
    /*@Inject(method = "enableScissor", at = @At("HEAD"), cancellable = true)
    private void cascade$enableScissor(ScreenRectangle rectangle, RenderPass renderPass, CallbackInfo ci) {
        if (!(rectangle instanceof CascadeScreenRectangle)) return;

        final Window window = Cascade.client.getWindow();
        final double scale = window.getGuiScale();
        final double left = rectangle.left() * scale;
        final double bottom = window.getHeight() - rectangle.bottom() * scale;
        final double width = rectangle.width() * scale;
        final double height = rectangle.height() * scale;

        renderPass.backend.enableScissor((int) left, (int) bottom, Math.max(0, (int) width), Math.max(0, (int) height));
        ci.cancel();
    }
    *///? }
}