/*
 * This file is part of TimeTriggeredPerms, licensed under the MIT License.
 *
 * Copyright (c) 2023 powercas_gamer
 * Copyright (c) 2023 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
