package top.nololiyt.worldpermissions.playerlisteners

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerTeleportEvent
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair

import java.io.File
import java.io.IOException

class LoginAndQuitListener(private val rootPlugin: RootPlugin) : Listener
{
    
    @EventHandler
    fun onPlayerLogin(e: PlayerJoinEvent)
    {
        if (!rootPlugin.config.getBoolean("offline-players-tracker.enabled") || rootPlugin.config.getBoolean(
                "offline-players-tracker.record-only"))
        {
            return;
        }
        
        val player = e.player;
        var file = File(rootPlugin.dataFolder.absolutePath, "playersData");
        file.mkdirs()
        file = File(file.absolutePath, player.uniqueId.toString() + ".yml")
        if (!file.exists()) return
        
        val yamlConfiguration = YamlConfiguration.loadConfiguration(file)
        val location = yamlConfiguration.get("position")
        if (location != null) player.teleport((location as Location?)!!)
    }
    
    @EventHandler
    fun onPlayerQuitEvent(e: PlayerQuitEvent)
    {
        if (!rootPlugin.config.getBoolean("offline-players-tracker.enabled"))
        {
            return
        }
        val player = e.player
        try
        {
            var file = File(rootPlugin.dataFolder.absolutePath, "playersData")
            file.mkdirs()
            file = File(file.absolutePath, player.uniqueId.toString() + ".yml")
            if (!file.exists()) file.createNewFile()
            
            val yamlConfiguration = YamlConfiguration.loadConfiguration(file)
            yamlConfiguration.set("position", player.location)
            yamlConfiguration.save(file)
        }
        catch (ex: IOException)
        {
            ex.printStackTrace()
        }
        
    }
}