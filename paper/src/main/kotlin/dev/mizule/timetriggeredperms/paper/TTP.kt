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
package dev.mizule.timetriggeredperms.paper

import dev.mizule.timetriggeredperms.core.TTPPlugin
import dev.mizule.timetriggeredperms.core.config.Config
import dev.mizule.timetriggeredperms.paper.command.ReloadCommand
import dev.mizule.timetriggeredperms.paper.listener.LuckPermsListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader

class TTP : JavaPlugin(), TTPPlugin<JavaPlugin> {

    private val configPath = dataFolder.resolve("permissions.yml")

    val configLoader = YamlConfigurationLoader.builder()
        .file(configPath)
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

    override fun onEnable() {
        if (!configPath.exists()) {
            configNode.set(config) // update the backing node to add defaults
            configLoader.save(configNode)
        }
        LuckPermsListener(this)

        Bukkit.getCommandMap().register("timetriggeredperms", ReloadCommand(this))
    }

    override fun config(): Config {
        return config
    }

    override fun plugin(): JavaPlugin {
        return this
    }
}
