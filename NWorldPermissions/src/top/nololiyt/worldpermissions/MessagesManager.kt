package top.nololiyt.worldpermissions

import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair

import java.io.*

class MessagesManager(private val rootPlugin: RootPlugin) {

    private val configuration: YamlConfiguration = YamlConfiguration();

    init {
        reloadConfiguration();
    }

    private fun saveDefaultFile(file: File) {
        val res = rootPlugin.getResource("messages.yml");

        var fileOutputStream: FileOutputStream? = null;
        try {
            file.parentFile.mkdirs();
            file.delete();
            file.createNewFile();

            fileOutputStream = FileOutputStream(file);

            val buffer = ByteArray(4096);
            while (true) {
                val count = res!!.read(buffer, 0, buffer.size);
                fileOutputStream.write(buffer, 0, count);
                if (count < buffer.size) {
                    break;
                }
            }
        } catch (e: IOException) {
            e.printStackTrace();
        } finally {
            fileOutputStream?.close();
        }

    }

    fun reloadConfiguration() {
        val file = File(rootPlugin.dataFolder.absolutePath, "messages.yml");
        if (!file.exists()) {
            saveDefaultFile(file);
        }
        configuration.load(file);
    }

    /*
    fun getMessage(node: DotDividedStringBuilder, stringPairs: Array<StringPair?>): String {
        val key = node.toString();
        val result = configuration.getString(key);

        if (result == null) {
            rootPlugin.logger.severe(
                "File 'messages.yml' is corrupted and '" + key
                        + "' is missing."
            );
            return "";
        }

        var r = result.trim().replace('&', '§');
        for (pair in stringPairs) {
            r = r.replace(pair!!.key, pair.value);
        }
        return r;
    }
*/
    fun sendMessage(node: DotDividedStringBuilder, stringPairs: Array<StringPair?>, target: CommandSender) {
        val key = node.toString();
        val result = configuration.getString(key);

        if (result == null) {
            rootPlugin.logger.severe(
                "File 'messages.yml' is corrupted and '" + key
                        + "' is missing."
            );
            return;
        }

        var r = result.trim().replace('&', '§');
        for (pair in stringPairs) {
            r = r.replace(pair!!.key, pair.value);
        }
        if (!r.isEmpty())
            target.sendMessage(r);
    }
}