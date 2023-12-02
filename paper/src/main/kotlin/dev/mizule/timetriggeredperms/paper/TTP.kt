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
import dev.mizule.timetriggeredperms.core.config.ConfigManager
import dev.mizule.timetriggeredperms.paper.command.ReloadCommand
import dev.mizule.timetriggeredperms.paper.listener.LuckPermsListener
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class TTP : JavaPlugin(), TTPPlugin<JavaPlugin> {

    private val configPath = dataFolder.resolve("permissions.yml").toPath()
    private val pluginId = 20404

    private lateinit var config: Config

    override fun onEnable() {
        this.config = ConfigManager.loadConfig(configPath)
        Metrics(this, pluginId)
        LuckPermsListener(this)

        Bukkit.getCommandMap().register("timetriggeredperms", ReloadCommand(this))
    }

    override fun config(): Config {
        return config
    }

    override fun reloadConfiguration() {
        this.config = ConfigManager.loadConfig(configPath)
    }

    override fun plugin(): JavaPlugin {
        return this
    }
}
