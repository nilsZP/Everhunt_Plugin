package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeleportData {
    private int playerID;
    private String location;
    private int x;
    private int y;
    private int z;

    public TeleportData(int playerID,String location,int x,int y,int z) {
        this.playerID = playerID;
        this.location = location;
        this.x = x;
        this.y = y;
        this.z = z;

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblteleport WHERE playerID = '" + playerID + "' AND location = '" + location + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblteleport (playerID, location, x, y, z) VALUES ('" + playerID + "','" + location + "','" + x + "','" + y + "','" +
                        z + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerLoot() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblteleport").executeQuery();

            while (resultSet.next()) {
                int playerID = resultSet.getInt("playerID");
                String location = resultSet.getString("location");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                int z = resultSet.getInt("z");

                new TeleportData(playerID,location,x,y,z);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getLocations(int playerID) {
        ArrayList<String> locationList = new ArrayList<>();
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
