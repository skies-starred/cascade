@file:Suppress("ConstPropertyName", "Unused")

package foo.starred.cascade

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import foo.starred.cascade.graphics.font.CascadeFonts
import foo.starred.cascade.wrappers.svg.impl.CascadeSVG
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.minecraft.client.Minecraft
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object Cascade : ClientModInitializer {
    const val version: String = /*$ mod_version*/ "2026.09.4"
    const val id: String = /*$ mod_id*/ "cascade"
    const val name: String = /*$ mod_name*/ "Cascade"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(Cascade::class.java)

    @JvmField
    val GSON: Gson = GsonBuilder().serializeNulls().create()

    @JvmField
    val client: Minecraft = Minecraft.getInstance()

    override fun onInitializeClient() {
        LOGGER.info("Cascade initialising...")
        CascadeFonts.toString()
        LOGGER.info("CascadeSVG will ${if (CascadeSVG.available) "" else "not "}be available.")
        LOGGER.info("Cascade initialised.")

        ClientLifecycleEvents.CLIENT_STARTED.register { _ ->
            val chars = (32..126).map { it.toChar() }
            CascadeFonts.sans.regular.preload(chars)
            CascadeFonts.sans.bold.preload(chars)
        }
    }
}
