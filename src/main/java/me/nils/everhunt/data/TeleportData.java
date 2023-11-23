package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeleportData {
    private String uuid;
    private String location;
    private int x;
    private int y;
    private int z;

    public TeleportData(String uuid,String location,int x,int y,int z) {
        this.uuid = uuid;
        this.location = location;
        this.x = x;
        this.y = y;
        this.z = z;

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblteleport WHERE uuid = '" + uuid + "' AND location = '" + location + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblteleport (uuid, location, x, y, z) VALUES ('" + uuid + "','" + location + "','" + x + "','" + y + "','" +
                        z + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerData() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblteleport").executeQuery();

            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String location = resultSet.getString("location");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                int z = resultSet.getInt("z");

                new TeleportData(uuid,location,x,y,z);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getLocations(String uuid) {
        ArrayList<String> locationList = new ArrayList<>();
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT location FROM tblteleport WHERE uuid = '" + uuid + "'").executeQuery();

            while (resultSet.next()) {
                locationList.add(resultSet.getString(1));
            }

            return locationList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationList;
    }

    public static Location getCoords(String uuid, String location) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT x,y,z FROM tblteleport WHERE uuid = '" + uuid + "' AND location = '" + location + "'").executeQuery();

            resultSet.next();

            Location loc = new Location(Bukkit.getServer().getWorld("world"),resultSet.getInt("x"),resultSet.getInt("y"),resultSet.getInt("z"));
            return loc;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
