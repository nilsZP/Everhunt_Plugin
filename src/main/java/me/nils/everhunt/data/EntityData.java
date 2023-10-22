package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EntityData {
    public static final HashMap<String, EntityData> data = new HashMap<>();
    private final String displayName;
    private final MobType type;
    private final Ability ability;
    private final int level;
    private final int maxHealth;

    public EntityData(String displayName, int level, int maxHealth, Ability ability, MobType type) {
        this.ability = ability;
        this.displayName = displayName;
        this.type = type;
        this.level = level;
        this.maxHealth = maxHealth;

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblentity WHERE displayname = '" + displayName + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblentity (displayname, level, maxhealth, ability, type) VALUES ('" + displayName + "','" + level + "','" + maxHealth + "','" + ability + "','" +
                        type + "')").executeUpdate();
                data.put(displayName,this);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerEntities() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblentity").executeQuery();

            while (resultSet.next()) {
                String displayName = resultSet.getString("displayname");
                int level = resultSet.getInt("level");
                int maxHealth = resultSet.getInt("maxhealth");
                MobType type = MobType.valueOf(resultSet.getString("type"));
                Ability ability = Ability.valueOf(resultSet.getString("ability"));

                new EntityData(displayName,level,maxHealth,ability,type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MobType getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
