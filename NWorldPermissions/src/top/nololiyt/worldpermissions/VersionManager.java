package top.nololiyt.worldpermissions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import top.nololiyt.worldpermissions.entitiesandtools.LatestVersion;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

public class VersionManager
{
    private Plugin plugin;
    private static final String CHECK_LINK =
            "https://yueyinqiu.github.io/NWorldPermissions/version.html";
    
    public VersionManager(Plugin plugin)
    {
        this.plugin = plugin;
        long oPeriod = plugin.getConfig().getLong("update-checker.period");
        long period = oPeriod <= 0 ? 20 * (30 * 60) : oPeriod;
        
        if (plugin.getConfig().getBoolean("update-checker.log-after-first-check"))
        {
            checkAndLog();
            startCheckLoop(period, period);
        }
        startCheckLoop(0, period);
    }
    
    private LatestVersion latestVersion;
    
    public LatestVersion getLatestVersion()
    {
        return latestVersion;
    }
    
    public BigDecimal getCurrentVersion()
    {
        return new BigDecimal(
                plugin.getDescription().getVersion());
    }
    
    private void checkAndLog()
    {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try
            {
                checkAndSave();
                if (this.latestVersion == null)
                {
                    log("Update check failed.");
                    return;
                }
    
                BigDecimal current = getCurrentVersion();
                if (latestVersion.getVersion().compareTo(current) > 0)
                    log("Version: '" + latestVersion.getVersion().toString() + "' is available. " +
                            "And you are now using '" + current.toString() + "'. " +
                            "Visit 'https://yueyinqiu.github.io/NWorldPermissions/download' to download it.");

                else
                    log("The current version is the latest.");
    
            }
            catch (Exception e)
            {
                log("Update check failed.");
                e.printStackTrace();
            }
        });
    }
    
    private void log(String s)
    {
        plugin.getLogger().warning(s);
    }
    
    private void checkAndSave() throws IOException
    {
        try (InputStream inputStream = new URL(CHECK_LINK)
                .openStream(); Scanner scanner = new Scanner(inputStream))
        {
            if (scanner.hasNextBigDecimal())
            {
                BigDecimal latest = scanner.nextBigDecimal();
                latestVersion = new LatestVersion(new Date(), latest);
            }
        }
    }
    
    private void startCheckLoop(long delay, long period)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    checkAndSave();
                }
                catch (Exception e)
                {
    
                }
                
            }
        }.runTaskTimerAsynchronously(plugin, delay, period);
    }
}