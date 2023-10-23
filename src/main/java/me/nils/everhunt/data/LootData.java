package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class LootData {
    private int entityID;
    private int itemID;
    private int minAmount;
    private int maxAmount;
    private int chance;

    public LootData(int entityID,int itemID,int minAmount,int maxAmount,int chance) {
        this.entityID = entityID;
        this.chance = chance;
        this.itemID = itemID;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblloot WHERE entityID = '" + entityID + "' AND itemID = '" + itemID + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblloot (entityID, itemID, minamount, maxamount, chance) VALUES ('" + entityID + "','" + itemID + "','" + minAmount + "','" + maxAmount + "','" +
                        chance + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerLoot() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblloot").executeQuery();

            while (resultSet.next()) {
                int entityID = resultSet.getInt("entityID");
                int itemID = resultSet.getInt("itemID");
                int minAmount = resultSet.getInt("minamount");
                int maxAmount = resultSet.getInt("maxamount");
                int chance = resultSet.getInt("chance");

                new LootData(entityID,itemID,minAmount,maxAmount,chance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
