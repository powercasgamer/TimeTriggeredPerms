package dev.mizule.timetriggeredperms.core.config

import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.nio.file.Path
import kotlin.io.path.exists

class ConfigManager {

    companion object {

        fun loadConfig(path: Path): Config {
            val configLoader = YamlConfigurationLoader.builder()
                .path(path)
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

            if (!path.exists()) {
                configNode.set(config) // update the backing node to add defaults
                configLoader.save(configNode)
            }

            return config
        }
    }
}