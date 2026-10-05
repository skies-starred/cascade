package foo.starred.cascade.mixin.mixins;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderTarget;
import foo.starred.cascade.internal.interceptor.data.CascadeDrawContext;
import foo.starred.cascade.internal.interceptor.impl.CascadeGenericDrawInterceptor;
import net.minecraft.client.gui.render.GuiRenderer;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Supplier;

//? if <= 26.1 {
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
//?}
//? if >= 26.2 {
/*import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderPass;
import foo.starred.cascade.Cascade;
import foo.starred.cascade.mixin.accessors.RenderPassAccessor;
import foo.starred.cascade.geometry.CascadeScreenRectangle;
import net.minecraft.client.gui.navigation.ScreenRectangle;
*///?}

@Mixin(GuiRenderer.class)
public class GuiRendererMixin {
    @Shadow
    @Final
    private List<GuiRenderer.Draw> draws;

    @Shadow
    //~ if >= 26.2 'GpuBufferSlice fogBuffer, GpuBufferSlice dynamicTransforms, GpuBuffer indexBuffer, VertexFormat.IndexType indexType' -> 'GpuBufferSlice dynamicTransforms'
    private void executeDrawRange(Supplier<String> label, RenderTarget mainRenderTarget, GpuBufferSlice fogBuffer, GpuBufferSlice dynamicTransforms, GpuBuffer indexBuffer, VertexFormat.IndexType indexType, int startIndex, int endIndex) {}

    @Inject(method = "executeDrawRange", at = @At("HEAD"), cancellable = true)
    //~ if >= 26.2 'GpuBufferSlice fogBuffer, GpuBufferSlice dynamicTransforms, GpuBuffer indexBuffer, VertexFormat.IndexType indexType' -> 'GpuBufferSlice dynamicTransforms'
    private void cascade$executeDrawRange(Supplier<String> label, RenderTarget mainRenderTarget, GpuBufferSlice fogBuffer, GpuBufferSlice dynamicTransforms, GpuBuffer indexBuffer, VertexFormat.IndexType indexType, int startIndex, int endIndex, CallbackInfo ci) {
        if (CascadeGenericDrawInterceptor.INSTANCE.extract(new CascadeDrawContext(this.draws, startIndex, endIndex, mainRenderTarget) {
            @Override
            public void execute(@NonNull RenderTarget target, int start, int end) {
                //~ if >= 26.2 'fogBuffer, dynamicTransforms, indexBuffer, indexType' -> 'dynamicTransforms'
                GuiRendererMixin.this.executeDrawRange(label, target, fogBuffer, dynamicTransforms, indexBuffer, indexType, start, end);
            }
        })) {
            ci.cancel();
        }
    }

    //? if >= 26.2 {
    /*@Inject(method = "enableScissor", at = @At("HEAD"), cancellable = true)
    private void cascade$enableScissor(ScreenRectangle rectangle, RenderPass renderPass, CallbackInfo ci) {
        if (!(rectangle instanceof CascadeScreenRectangle)) return;
        RenderPassAccessor pass = (RenderPassAccessor) renderPass;

        final Window window = Cascade.client.getWindow();
        final double scale = window.getGuiScale();
        final double left = Math.max(0.0, rectangle.left() * scale);
        final double top = Math.max(0.0, rectangle.top() * scale);
        final double right = Math.min((double) window.getWidth(), rectangle.right() * scale);
        final double bottom = Math.min((double) window.getHeight(), rectangle.bottom() * scale);

        pass.cascade$backend().enableScissor((int) left, Math.max(0, window.getHeight() - (int) bottom), Math.max(0, (int) (right - left)), Math.max(0, (int) (bottom - top)));
        ci.cancel();
    }
    *///? }
}
