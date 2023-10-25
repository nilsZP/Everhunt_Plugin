package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LootData {
    private int entityID;
    private int itemID;
    private int minAmount;
    private int maxAmount;
    private double chance;

    public LootData(int entityID,int itemID,int minAmount,int maxAmount,double chance) {
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
                double chance = resultSet.getDouble("chance");

                new LootData(entityID,itemID,minAmount,maxAmount,chance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> getItemIDs(int entityID) {
        ArrayList<Integer> itemIDList = new ArrayList<>();
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT itemID FROM tblloot WHERE entityID = '" + entityID + "'").executeQuery();

            while (resultSet.next()) {
                itemIDList.add(resultSet.getInt(1));
            }

            return itemIDList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemIDList;
    }

    public static int getMinimum(int entityID, int itemID) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT minamount FROM tblloot WHERE entityID = '" + entityID + "' AND itemID = '" + itemID + "'").executeQuery();

            resultSet.next();

            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getMaximum(int entityID, int itemID) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT maxamount FROM tblloot WHERE entityID = '" + entityID + "' AND itemID = '" + itemID + "'").executeQuery();

            resultSet.next();

            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static double getChance(int entityID, int itemID) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT chance FROM tblloot WHERE entityID = '" + entityID + "' AND itemID = '" + itemID + "'").executeQuery();

            resultSet.next();

            return resultSet.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
