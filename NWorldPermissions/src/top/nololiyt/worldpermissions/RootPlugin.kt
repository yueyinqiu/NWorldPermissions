package top.nololiyt.worldpermissions


import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import top.nololiyt.worldpermissions.playerlisteners.LoginAndQuitListener
import top.nololiyt.worldpermissions.playerlisteners.TeleportListener

class RootPlugin : JavaPlugin() {
    var messagesManager: MessagesManager? = null
        private set


    var marksManager: MarksManager? = null
        private set

    override fun onEnable() {
        saveDefaultConfig()
        messagesManager = MessagesManager(this)
        marksManager = MarksManager(this)

        getCommand("nworldpermissions")!!.setExecutor(RootCommandExecutor(this))

        Bukkit.getPluginManager().registerEvents(
                LoginAndQuitListener(this), this)
        Bukkit.getPluginManager().registerEvents(
                TeleportListener(this), this)

        UpdateChecker(this).checkAndLog()
    }
}
