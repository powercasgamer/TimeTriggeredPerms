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
package dev.mizule.timetriggeredperms.paper.listener

import dev.mizule.timetriggeredperms.core.TTPPlugin
import dev.mizule.timetriggeredperms.core.config.PermissionThing
import dev.mizule.timetriggeredperms.core.listener.AbstractLuckPermsListener
import net.luckperms.api.event.node.NodeRemoveEvent
import net.luckperms.api.model.user.User
import net.luckperms.api.node.types.PermissionNode
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

class LuckPermsListener(private val plugin: TTPPlugin<JavaPlugin>) : AbstractLuckPermsListener(plugin) {

    override fun onExpire(event: NodeRemoveEvent) {
        val permissionNode = event.node as PermissionNode

        val configNode = nodeConfig(permissionNode.permission) ?: return

        val isUser = event.isUser

        sync {
            configNode.commands.forEach { command ->
                val name = event.target.friendlyName
                val uuid = if (isUser) (event.target as User).uniqueId.toString() else ""
                val formattedCommand = command
                    .replace("%name%", name)
                    .replace("%uuid%", uuid)
                    .replace("%permission%", permissionNode.permission)

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand)
            }
        }
    }

    fun nodeConfig(perm: String): PermissionThing? {
        return plugin.config().permissions.values.firstOrNull { it.permission == perm }
    }

    fun sync(task: (BukkitTask) -> Unit) {
        Bukkit.getScheduler().runTask(plugin.plugin(), task)
    }
}
