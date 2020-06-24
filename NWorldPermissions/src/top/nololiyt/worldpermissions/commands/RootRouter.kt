package top.nololiyt.worldpermissions.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.commands.reload.ReloadRouter
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.commands.config.ConfigRouter
import top.nololiyt.worldpermissions.commands.marks.MarksRouter
import top.nololiyt.worldpermissions.commands.tp.TpRouter

class RootRouter(private val rootPlugin: RootPlugin) : Router()
{
    fun routeCommand(commandSender: CommandSender, args: Array<String>)
    {
        val messagesRoot = DotDividedStringBuilder("messages");
        val permissionRoot = DotDividedStringBuilder("nworldpermissions");
        
        execute(0, rootPlugin, permissionRoot, messagesRoot, commandSender, args);
    }
    
    
    override fun permissionName(): String?
    {
        return null;
    }
    
    override fun messageKey(): String?
    {
        return null;
    }
    
    /**
     * Return the next layer matching the arg.
     * If no layer match it, please return 'null' and the help list will be sent.
     *
     * @param arg
     * @return
     */
    override fun nextLayer(arg: String): CommandLayer?
    {
        return when (arg)
        {
            "config" -> ConfigRouter()
            "tp"     -> TpRouter()
            "marks"  -> MarksRouter()
            "reload" -> ReloadRouter()
            else     -> null
        }
    }
}