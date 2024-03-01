package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CostNPCData {
    public static final HashMap<String, CostNPCData> data = new HashMap<>();
    private String name;
    private int cost;

    public CostNPCData(String name, int cost) {
        this.name = name;
        this.cost = cost;

        data.put(name,this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblcostnpc WHERE name = '" + name + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblcostnpc (name,cost) VALUES ('" + name + "','" + cost + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerData() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblcostnpc").executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int cost = resultSet.getInt("cost");

                new CostNPCData(name,cost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCost() {
        return cost;
    }
}
