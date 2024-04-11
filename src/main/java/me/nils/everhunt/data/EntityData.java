package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import org.bukkit.entity.LivingEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class EntityData {
    public static final HashMap<String, EntityData> data = new HashMap<>();
    private final String displayName;
    private final MobType type;
    private final Tier tier;
    private LivingEntity entity;

    public EntityData(String displayName, Tier tier, MobType type) {
        this.displayName = displayName;
        this.type = type;
        this.tier = tier;

        data.put(displayName,this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblentity WHERE displayname = '" + displayName + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblentity (displayname, tier, type) VALUES ('" + displayName + "','" + tier + "','" +
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
                Tier tier = Tier.valueOf(resultSet.getString("tier"));
                MobType type = MobType.valueOf(resultSet.getString("type"));

                new EntityData(displayName,tier,type);
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

    public LivingEntity getEntity() {
        return entity;
    }

    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }
}
