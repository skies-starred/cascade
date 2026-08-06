@file:Suppress("ConstPropertyName")

package foo.starred.cascade

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import foo.starred.cascade.font.CascadeFonts
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.minecraft.client.Minecraft
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object Cascade : ClientModInitializer {
    const val version: String = /*$ mod_version*/ "001"
    const val id: String = /*$ mod_id*/ "cascade"
    const val name: String = /*$ mod_name*/ "Cascade"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(Cascade::class.java)

    @JvmField
    val GSON: Gson = GsonBuilder().serializeNulls().create()

    @JvmField
    val client: Minecraft = Minecraft.getInstance()

    override fun onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register { _ ->
            CascadeFonts.init()
        }
    }
}