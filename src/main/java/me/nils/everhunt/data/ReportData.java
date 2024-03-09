package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ReportData {
    public ReportData(Player reporter,String reported, String report) {
        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblreport WHERE reporter = '" + reporter.getName() + "' AND reported = '" + reported + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblreport (reporter,reported, report) VALUES ('" + reporter.getName() + "','" + reported +
                        "','" + report + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<ItemStack> getReports(String username) {
        ArrayList<ItemStack> list = new ArrayList<>();

        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblreport where reported = '" + username + "'").executeQuery();

            while (resultSet.next()) {
                String reporter = resultSet.getString("reporter");
                String report = resultSet.getString("report");

                list.add(Menu.makeItem(Material.PAPER,username,report,"Reported by " + reporter));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<ItemStack> getReports() {
        ArrayList<ItemStack> list = new ArrayList<>();

        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblreport").executeQuery();

            while (resultSet.next()) {
                String reported = resultSet.getString("reported");
                String reporter = resultSet.getString("reporter");
                String report = resultSet.getString("report");

                list.add(Menu.makeItem(Material.PAPER,reported,report,"Reported by " + reporter));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}

