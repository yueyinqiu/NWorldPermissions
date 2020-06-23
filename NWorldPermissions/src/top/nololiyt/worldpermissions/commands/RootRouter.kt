package top.nololiyt.worldpermissions.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.commands.reload.ReloadRouter
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.commands.config.ConfigRouter
import top.nololiyt.worldpermissions.commands.marks.MarksRouter
import top.nololiyt.worldpermissions.commands.tp.TpRouter

class RootRouter(private val rootPlugin: RootPlugin) : Router() {


    fun RouteCommand(commandSender: CommandSender, command: Command,
                     label: String, args: Array<String>) {
        val messagesRoot = DotDividedStringBuilder("messages")
        val permissionRoot = DotDividedStringBuilder("nworldpermissions")

        execute(0, rootPlugin, permissionRoot, messagesRoot, commandSender, args)

    }


    override fun permissionName(): String? {
        return null
    }

    override fun messageKey(): String? {
        return null
    }

    /**
     * Return the next layer matching the arg.
     * If no layer match it, please return 'null' and the help list will be sent.
     *
     * @param arg
     * @return
     */
    override fun nextLayer(arg: String): CommandLayer? {
        when (arg) {
            "config" -> return ConfigRouter()
            "tp" -> return TpRouter()
            "marks" -> return MarksRouter()
            "reload" -> return ReloadRouter()
            else -> return null
        }
    }
}
