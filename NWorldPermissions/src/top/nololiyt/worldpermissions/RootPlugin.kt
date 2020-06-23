package top.nololiyt.worldpermissions


import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import top.nololiyt.worldpermissions.playerlisteners.LoginAndQuitListener
import top.nololiyt.worldpermissions.playerlisteners.TeleportListener

class RootPlugin : JavaPlugin() {

    val messagesManager: MessagesManager = MessagesManager(this);
    val marksManager: MarksManager = MarksManager(this);

    override fun onEnable() {
        saveDefaultConfig();

        getCommand("nworldpermissions")!!.setExecutor(
            RootCommandExecutor(this));

        Bukkit.getPluginManager().registerEvents(
                LoginAndQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(
                TeleportListener(this), this);

        UpdateChecker(this).checkAndLog();
    }
}
