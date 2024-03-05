package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class EntityData {
    public static final HashMap<String, EntityData> data = new HashMap<>();
    private final String displayName;
    private final MobType type;
    private final Ability ability;
    private final int level;
    private final Tier tier;
    private Mob entity;

    public EntityData(String displayName, int level, Tier tier, Ability ability, MobType type) {
        this.ability = ability;
        this.displayName = displayName;
        this.type = type;
        this.level = level;
        this.tier = tier;

        data.put(displayName,this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblentity WHERE displayname = '" + displayName + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblentity (displayname, level, tier, ability, type) VALUES ('" + displayName + "','" + level + "','" + tier + "','" + ability + "','" +
                        type + "')").executeUpdate();
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
                Tier tier = Tier.valueOf(resultSet.getString("tier"));
                MobType type = MobType.valueOf(resultSet.getString("type"));
                Ability ability = Ability.valueOf(resultSet.getString("ability"));

                new EntityData(displayName,level,tier,ability,type);
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

    public Ability getAbility() {
        return ability;
    }

    public Mob getEntity() {
        return entity;
    }

    public void setEntity(Mob entity) {
        this.entity = entity;
    }
}
