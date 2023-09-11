package me.nils.vdvcraftrevamp.data;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.MobType;
import me.nils.vdvcraftrevamp.managers.FileManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class EntityData {
    public static final HashMap<String, EntityData> entities = new HashMap<>();
    private final String displayName;
    private final MobType type;
    private final Ability ability;
    private final YamlConfiguration configuration;

    public EntityData(String displayName, Ability ability, MobType type) {
        this.ability = ability;
        this.displayName = displayName;
        this.type = type;

        FileManager fileManager = new FileManager("entities", displayName);
        configuration = fileManager.getFile();
        configuration.set("displayName", displayName);
        configuration.set("type", type.toString());
        configuration.set("ability", ability.toString());

        fileManager.save();
        entities.put(displayName, this);
    }

    public static void registerEntities() {
        List<File> files = FileManager.getFiles("entities");
        if (files == null) {
            return;
        }
        for (File file : files) {
            YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

            String displayName = fileConfiguration.getString("displayName");
            MobType type = MobType.valueOf(fileConfiguration.getString("type"));
            Ability ability = Ability.valueOf(fileConfiguration.getString("ability"));

            new EntityData(displayName, ability, type);
        }
    }

    public MobType getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }
}
