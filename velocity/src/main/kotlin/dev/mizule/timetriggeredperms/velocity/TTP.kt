package dev.mizule.timetriggeredperms.velocity

import com.google.inject.Inject
import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Dependency
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import dev.mizule.timetriggeredperms.core.TTPPlugin
import dev.mizule.timetriggeredperms.core.config.Config
import dev.mizule.timetriggeredperms.core.config.ConfigManager
import dev.mizule.timetriggeredperms.velocity.listener.LuckPermsListener
import org.bstats.velocity.Metrics
import org.slf4j.Logger
import xyz.jpenilla.gremlin.runtime.DependencyCache
import xyz.jpenilla.gremlin.runtime.DependencyResolver
import xyz.jpenilla.gremlin.runtime.DependencySet
import xyz.jpenilla.gremlin.runtime.platformsupport.VelocityClasspathAppender
import java.nio.file.Path


class TTP @Inject constructor(
    logger: Logger,
    val proxy: ProxyServer,
    val dataPath: Path
) : TTPPlugin<PluginLoader> {

    private val configPath = dataPath.resolve("permissions.yml")
    private lateinit var config: Config

    fun enable() {
        this.config = ConfigManager.loadConfig(configPath)
        LuckPermsListener(PluginLoader.instance)
    }

    override fun config(): Config {
        return config
    }

    override fun plugin(): PluginLoader {
        return PluginLoader.instance;
    }
}