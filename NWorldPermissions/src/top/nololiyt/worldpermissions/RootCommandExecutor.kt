package top.nololiyt.worldpermissions

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import top.nololiyt.worldpermissions.commands.RootRouter

class RootCommandExecutor(rootPlugin: RootPlugin) : CommandExecutor {

    private val router: RootRouter = RootRouter(rootPlugin)

    override fun onCommand(
        commandSender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): Boolean {
        router.RouteCommand(commandSender, command, label, args);
        return true;
    }
}