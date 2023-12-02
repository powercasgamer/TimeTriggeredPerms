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

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment
import org.spongepowered.configurate.objectmapping.meta.Setting

@ConfigSerializable
data class Config(

    @Setting(nodeFromParent = true)
    val permissions: Map<String, PermissionThing> = mapOf(
        "example" to PermissionThing(
            "test.permission",
            listOf("say example"),
            Target.USER
        ),
    ),

)

@ConfigSerializable
data class PermissionThing(

    @Comment("The permission to check for. Has to be an exact match")
    val permission: String,

    @Comment(
        "The commands to execute when a permission expires.\n" +
            "Available placeholders are:\n" +
            "%name% - the group or player name\n" +
            "%uuid% - the player's uuid or empty if it's a group\n" +
            "%permission% - the permission that got removed",
    )
    val commands: List<String>,

    @Comment(
        "Available options are:\n" +
                "USER - If this should be called for Users only\n" +
                "GROUP - If this should be called for Groups only\n" +
                "ALL - If this should be called for Users and Groups"
    )
    val target: Target
)

enum class Target {
    USER,
    GROUP,
    ALL,
}
