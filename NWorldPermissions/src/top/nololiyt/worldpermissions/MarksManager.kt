package top.nololiyt.worldpermissions

import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class MarksManager(private val rootPlugin: RootPlugin)
{
    
    private val configuration: YamlConfiguration = YamlConfiguration();
    
    private val marksFile: File
        get() = File(rootPlugin.dataFolder.absolutePath, "marks.yml")
    
    init
    {
        reloadConfiguration();
    }
    
    private fun saveDefaultFile(file: File)
    {
        val res = rootPlugin.getResource("marks.yml");
        
        var fileOutputStream: FileOutputStream? = null;
        
        try
        {
            file.parentFile.mkdirs();
            file.delete();
            file.createNewFile();
            
            fileOutputStream = FileOutputStream(file);
            
            val buffer = ByteArray(4096);
            while (true)
            {
                val count = res!!.read(buffer, 0, buffer.size);
                fileOutputStream.write(buffer, 0, count);
                if (count < buffer.size)
                {
                    break;
                }
            }
            
        }
        catch (e: IOException)
        {
            e.printStackTrace();
        }
        finally
        {
            fileOutputStream?.close();
        }
    }
    
    fun reloadConfiguration()
    {
        val file = marksFile;
        if (!file.exists())
        {
            saveDefaultFile(file);
        }
        configuration.load(file);
    }
    
    fun setMark(name: String, mark: Location?)
    {
        configuration.set(name, mark);
        configuration.save(marksFile);
    }
    
    fun getMark(name: String): Location?
    {
        val oLocation = configuration.get(name) ?: return null;
        return oLocation as Location;
    }
}