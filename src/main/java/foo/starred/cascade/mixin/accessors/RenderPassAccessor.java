package foo.starred.cascade.mixin.accessors;

//~ if >= 26.3 'blaze3d.systems.RenderPass' -> 'renderpearl.frontend.FrontendRenderPass'
import com.mojang.blaze3d.systems.RenderPass;
//~ if >= 26.3 'blaze3d.systems' -> 'renderpearl.backend.api'
import com.mojang.blaze3d.systems.RenderPassBackend;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//~ if >= 26.3 'RenderPass' -> 'FrontendRenderPass'
@Mixin(RenderPass.class)
public interface RenderPassAccessor {
    @Accessor("backend")
    RenderPassBackend cascade$backend();
}
