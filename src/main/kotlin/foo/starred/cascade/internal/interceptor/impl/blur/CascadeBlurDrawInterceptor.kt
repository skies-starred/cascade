package foo.starred.cascade.internal.interceptor.impl.blur

import foo.starred.cascade.graphics.states.impl.blur.BlurRenderState
import foo.starred.cascade.internal.blur.impl.CascadeBlurSetup
import foo.starred.cascade.internal.interceptor.base.ICascadeDrawInterceptor
import foo.starred.cascade.internal.interceptor.data.CascadeDrawContext

object CascadeBlurDrawInterceptor : ICascadeDrawInterceptor {
    override fun intercept(context: CascadeDrawContext): Boolean {
        val first = context.index(BlurRenderState.PIPELINE)

        if (first == -1) {
            return false
        }

        if (first == context.start) {
            CascadeBlurSetup.capture()
            return false
        }

        context.execute(context.target, context.start, first)
        CascadeBlurSetup.capture()
        context.execute(context.target, first, context.end)

        return true
    }
}
