package foo.starred.cascade.internal.interceptor.impl.scissor

import foo.starred.cascade.internal.interceptor.base.ICascadeDrawInterceptor
import foo.starred.cascade.internal.interceptor.data.CascadeDrawContext
import foo.starred.cascade.internal.scissor.rounded.data.CascadeRoundedScissorPipeline
import foo.starred.cascade.internal.scissor.rounded.impl.CascadeRoundedScissorSetup

object CascadeRoundedScissorDrawInterceptor : ICascadeDrawInterceptor {
    override fun intercept(context: CascadeDrawContext): Boolean {
        val start = context.index(CascadeRoundedScissorPipeline.START)
        if (start == -1) {
            return false
        }

        if (start > context.start) {
            context.execute(context.target, context.start, start)
            context.execute(context.target, start, context.end)
            return true
        }

        val end = context.index(CascadeRoundedScissorPipeline.BLIT, from = start + 1)
        if (end == -1) {
            return false
        }

        if (end == start + 1) {
            if (end + 1 < context.end) context.execute(context.target, end + 1, context.end)
            return true
        }

        CascadeRoundedScissorSetup.validate(context.target.width, context.target.height)
        CascadeRoundedScissorSetup.clear()

        context.execute(CascadeRoundedScissorSetup.target!!, start + 1, end)
        context.execute(context.target, end, context.end)
        return true
    }
}
