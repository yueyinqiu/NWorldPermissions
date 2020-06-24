package top.nololiyt.worldpermissions.commands

import org.bukkit.command.CommandSender
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder

interface CommandLayer
{
    fun execute(layer: Int, rootPlugin: RootPlugin, permission: DotDividedStringBuilder,
        messageKey: DotDividedStringBuilder, commandSender: CommandSender, args: Array<String>)
}