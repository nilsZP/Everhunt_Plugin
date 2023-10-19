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

    public EntityData(String displayName, Ability ability, MobType type) {
        this.ability = ability;
        this.displayName = displayName;
        this.type = type;

        try {
            Everhunt.getDatabase().run("INSERT INTO tblentity (displayname, ability, type) VALUES (" + displayName + "," + ability + "," +
                    type).executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        data.put(displayName,this);
    }

    public static void registerEntities() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblentity").executeQuery();

            while (resultSet.next()) {
                String displayName = resultSet.getString("displayname");
                MobType type = MobType.valueOf(resultSet.getString("tier"));
                Ability ability = Ability.valueOf(resultSet.getString("ability"));

                ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblentity WHERE displayname = " + displayName).executeQuery();
                check.next();
                if (check.getInt(1) < 1) {
                    new EntityData(displayName,ability,type);
                }
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
}
