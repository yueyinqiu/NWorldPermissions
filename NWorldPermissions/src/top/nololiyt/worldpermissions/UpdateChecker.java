package top.nololiyt.worldpermissions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker
{
    private Plugin plugin;
    
    public UpdateChecker(Plugin plugin)
    {
        this.plugin = plugin;
    }
    
    public void checkAndLog()
    {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL(
                    "https://yueyinqiu.github.io/NWorldPermissions/version.html")
                    .openStream(); Scanner scanner = new Scanner(inputStream))
            {
                if (scanner.hasNextBigDecimal())
                {
                    BigDecimal latest = scanner.nextBigDecimal();
                    BigDecimal current = new BigDecimal(
                            plugin.getDescription().getVersion()
                    );
                    
                    if (latest.compareTo(current) > 0)
                    {
                        plugin.getLogger().warning("Version: '" + latest.toString() + "' is available. " +
                                "And you are now using '" + current.toString() + "'. " +
                                "Visit 'https://yueyinqiu.github.io/NWorldPermissions/download' to download the newer version.");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }
    /*
    private String readToEnd(InputStream inputStream)
    {
        Scanner scanner = new Scanner(inputStream);
        StringBuilder stringBuilder = new StringBuilder();
        while(scanner.hasNextLine())
        {
            stringBuilder.append(scanner.nextLine());
        }
        return stringBuilder.toString();
    }
    */
}