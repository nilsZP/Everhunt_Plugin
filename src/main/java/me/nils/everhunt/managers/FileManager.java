package me.nils.everhunt.managers;

import me.nils.everhunt.Everhunt;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileManager {

    private static final File dataFolder = Everhunt.getInstance().getDataFolder();

    private final File file;
    private final YamlConfiguration editableFile;

    public FileManager(String path, String fileName) {
        file = new File(dataFolder + File.separator + path.replace("/", File.separator), fileName + ".yml");
        file.getParentFile().mkdirs();

        if (!(file.exists())) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        editableFile = YamlConfiguration.loadConfiguration(file);
    }

    public FileManager(String fileName) {
        this(dataFolder.getPath(), fileName);
    }

    public static List<File> getFiles(String folder) {
        String path = dataFolder + File.separator + folder.replace("/", File.separator);
        File[] files = new File(path).listFiles();
        if (files != null && files.length > 0) {
            return List.of(files);
        }

        return null;
    }

    public static File getFile(String folder, String fileName) {
        String path = dataFolder + File.separator + folder.replace("/", File.separator);
        File file = new File(path, fileName + ".yml");
        if (file.exists()) {
            return file;
        }

        return null;
    }

    public YamlConfiguration getFile() {
        return editableFile;
    }

    public void save() {
        try {
            editableFile.save(file);
        } catch (Exception e) {

        }
    }
}
