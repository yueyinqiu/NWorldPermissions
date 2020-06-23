package top.nololiyt.worldpermissions

import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class MarksManager internal constructor(private val rootPlugin: RootPlugin) {

    private var configuration: YamlConfiguration? = null

    private val marksFile: File
        get() = File(
                rootPlugin.dataFolder.absolutePath, "marks.yml")

    init {
        val file = marksFile
        if (!file.exists()) {
            saveDefaultFile(file)
        }
        reloadConfiguration()
    }

    private fun saveDefaultFile(file: File) {
        val res = rootPlugin.getResource("marks.yml")
        try {
            file.parentFile.mkdirs()
            file.delete()
            file.createNewFile()
            val fileOutputStream = FileOutputStream(file)

            val buffer = ByteArray(4096)
            while (true) {
                val count = res!!.read(buffer, 0, buffer.size)
                fileOutputStream.write(buffer, 0, count)
                if (count < buffer.size) {
                    break
                }
            }

            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun reloadConfiguration() {
        configuration = YamlConfiguration.loadConfiguration(
                marksFile)
    }

    fun setMark(name: String, mark: Location?) {
        configuration!!.set(name, mark)
        configuration!!.save(marksFile)
    }

    fun getMark(name: String): Location? {
        val oLocation = configuration!!.get(name) ?: return null
        return oLocation as Location
    }
}