package me.nils.vdvcraftrevamp.data;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.Flow;
import me.nils.vdvcraftrevamp.constants.MobType;
import me.nils.vdvcraftrevamp.constants.Rarity;
import me.nils.vdvcraftrevamp.managers.FileManager;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import me.nils.vdvcraftrevamp.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.*;

public class EntityData {
    public static final HashMap<String, EntityData> entities = new HashMap<>();
    private final String displayName;
    private final MobType type;
    private final Ability ability;
    private final Flow flow;
    private final YamlConfiguration configuration;

    public EntityData(String displayName, Ability ability, MobType type, Flow flow) {
        this.ability = ability;
        this.displayName = displayName;
        this.type = type;
        this.flow = flow;

        FileManager fileManager = new FileManager("entities", displayName);
        configuration = fileManager.getFile();
        configuration.set("displayName", displayName);
        configuration.set("type", type.toString());
        configuration.set("ability", ability.toString());
        configuration.set("flow", flow.toString());

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
            Flow flow = Flow.valueOf(fileConfiguration.getString("flow"));

            new EntityData(displayName, ability, type, flow);
        }
    }

    public MobType getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }
}
