package top.nololiyt.worldpermissions

import org.bukkit.configuration.file.YamlConfiguration
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair

import java.io.*

class MessagesManager(private val rootPlugin: RootPlugin) {

    private var configuration: YamlConfiguration? = null

    private val messagesFile: File
        get() = File(rootPlugin.dataFolder.absolutePath, "messages.yml");

    init {
        val file = messagesFile
        if (!file.exists()) {
            saveDefaultFile(file)
        }
        reloadConfiguration()
    }

    private fun saveDefaultFile(file: File) {
        val res = rootPlugin.getResource("messages.yml")
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
                messagesFile)
    }

    fun getMessage(node: DotDividedStringBuilder, stringPairs: Array<StringPair?>): String {
        val key = node.toString()
        var result = configuration!!.getString(key)

        if (result == null) {
            rootPlugin.logger.severe(
                    "File 'messages.yml' is corrupted and '" + key
                            + "' is missing.")
            return ""
        }

        var r = result.trim().replace('&', '§')
        for (pair in stringPairs) {
            r = r.replace(pair!!.key, pair!!.value)
        }
        return r
    }
}