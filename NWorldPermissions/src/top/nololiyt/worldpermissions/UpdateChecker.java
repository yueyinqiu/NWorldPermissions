package top.nololiyt.worldpermissions;

import jdk.internal.util.xml.impl.ReaderUTF8;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
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
                    BigDecimal ver = scanner.nextBigDecimal();
                    BigDecimal current = new BigDecimal(
                            plugin.getDescription().getVersion()
                    );
                    if (ver.compareTo(current) > 0)
                    {
                        plugin.getLogger().warning("Version: '" + ver.toString() + "' available. " +
                                "You are now using '" + current.toString() + "'.");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }
    
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
}
