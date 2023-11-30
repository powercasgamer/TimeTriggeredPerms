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
import dev.mizule.timetriggeredperms.paper.listener.LuckPermsListener
import org.slf4j.Logger
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import xyz.jpenilla.gremlin.runtime.DependencyCache
import xyz.jpenilla.gremlin.runtime.DependencyResolver
import xyz.jpenilla.gremlin.runtime.DependencySet
import xyz.jpenilla.gremlin.runtime.platformsupport.PaperClasspathAppender
import xyz.jpenilla.gremlin.runtime.platformsupport.VelocityClasspathAppender
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists


@Plugin(
    name = "TimeTriggeredPerms",
    id = "timetriggeredperms",
    version = "@version@",
    authors = ["powercas_gamer"],
    dependencies = [Dependency(id = "luckperms", optional = false)]
)
class TTP @Inject constructor(
    logger: Logger,
    @DataDirectory val dataPath: Path,
    val proxy: ProxyServer,
) : TTPPlugin<TTP> {

    init {
        val deps = DependencySet.readDefault(this.javaClass.classLoader)
        val cache = DependencyCache(dataPath.resolve("libraries"))
        DependencyResolver(logger).use { downloader ->
            VelocityClasspathAppender(this.proxy, this).append(downloader.resolve(deps, cache).jarFiles())
        }
        cache.cleanup()
    }

    private val configPath = dataPath.resolve("permissions.yml")
    private val pluginId = 20405

    val configLoader = YamlConfigurationLoader.builder()
        .path(configPath)
        .nodeStyle(NodeStyle.BLOCK)
        .indent(2)
        .defaultOptions { options ->
            options.shouldCopyDefaults(true)
            options.serializers { builder ->
                builder.registerAnnotatedObjects(objectMapperFactory())
            }
        }
        .build()

    var configNode = configLoader.load()
    var config = requireNotNull(configNode.get<Config>()) {
        "Could not read configuration"
    }

    @Subscribe(order = PostOrder.LATE)
    fun onProxyInitialize(event: ProxyInitializeEvent) {
        if (!configPath.exists()) {
            configNode.set(config) // update the backing node to add defaults
            configLoader.save(configNode)
        }
//        Metrics(this, pluginId)
        LuckPermsListener(this)
    }

    override fun config(): Config {
        return config
    }

    override fun plugin(): TTP {
        return this
    }
}