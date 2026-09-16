package foo.starred.cascade.mixin.accessors;

//? if >= 26.3 {
/*import com.mojang.renderpearl.frontend.FrontendRenderPass;
import com.mojang.renderpearl.backend.api.RenderPassBackend;
*///?} else {
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderPassBackend;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//? if >= 26.3 {
/*@Mixin(FrontendRenderPass.class)
*///?} else {
@Mixin(RenderPass.class)
//?}
public interface RenderPassAccessor {
    @Accessor("backend")
    RenderPassBackend cascade$backend();
}
