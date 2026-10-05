package foo.starred.cascade.internal.interceptor.impl

import foo.starred.cascade.internal.interceptor.base.ICascadeDrawInterceptor
import foo.starred.cascade.internal.interceptor.data.CascadeDrawContext
import foo.starred.cascade.internal.interceptor.impl.blur.CascadeBlurDrawInterceptor
import foo.starred.cascade.internal.interceptor.impl.scissor.CascadeRoundedScissorDrawInterceptor

object CascadeGenericDrawInterceptor {
    private val interceptors = mutableListOf(CascadeRoundedScissorDrawInterceptor, CascadeBlurDrawInterceptor)

    fun add(interceptor: ICascadeDrawInterceptor) {
        interceptors.add(interceptor)
    }

    fun extract(context: CascadeDrawContext): Boolean {
        for (interceptor in interceptors) {
            if (!interceptor.intercept(context)) continue

            return true
        }

        return false
    }
}
