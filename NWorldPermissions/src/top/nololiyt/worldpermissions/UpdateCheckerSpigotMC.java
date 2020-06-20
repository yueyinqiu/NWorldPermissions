package top.nololiyt.worldpermissions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateCheckerSpigotMC
{
    private Plugin plugin;
    
    public UpdateCheckerSpigotMC(Plugin plugin)
    {
        this.plugin = plugin;
    }
    
    public void checkAndLog()
    {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL(
                    "https://api.spigotmc.org/legacy/update.php?resource=80417").openStream();
                 Scanner scanner = new Scanner(inputStream))
            {
                if (scanner.hasNext())
                {
                    String ver = scanner.next();
                    if (!plugin.getDescription().getVersion().equalsIgnoreCase(ver))
                        plugin.getLogger().warning("There is a new update available. " +
                                "At least on 'www.spigotmc.org' is. " +
                                "You may visit 'https://www.spigotmc.org/resources/nworldpermissions.80417/'.");
                }
            }
            catch (IOException exception)
            {
                this.plugin.getLogger().warning("Cannot look for updates: " + exception.getMessage());
            }
        });
    }
}