package foo.starred.cascade.internal.interceptor.base

import foo.starred.cascade.internal.interceptor.data.CascadeDrawContext

fun interface ICascadeDrawInterceptor {
    fun intercept(context: CascadeDrawContext): Boolean
}
