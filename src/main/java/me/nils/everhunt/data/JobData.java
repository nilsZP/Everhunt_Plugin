package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Job;
import me.nils.everhunt.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JobData {
    private String uuid;
    private Job job;
    private int xp;

    public JobData(String uuid, Job job, int xp) {
        this.uuid = uuid;
        this.job = job;
        this.xp = xp;

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tbljob WHERE UUID = '" + uuid + "' AND job = '" +
                    job + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tbljob (uuid, job, xp) VALUES ('" + uuid + "','" + job + "','" +
                        xp + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getXp(String uuid, Job job) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT xp FROM tbljob WHERE uuid = '" + uuid + "' AND job = '" +
                    job + "'").executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getLevel(String uuid, Job job) {
        return getXp(uuid,job)/100;
    }

    public static void setXp(String uuid, Job job, int xp) {
        try {
            Everhunt.getDatabase().run("UPDATE tbljob SET xp = " + xp + " WHERE uuid = '" + uuid + "' AND job = '" +
                    job + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addXp(String uuid, Job job, int xp) {
        if (hasJob(uuid,job)) {
            setXp(uuid, job, getXp(uuid, job) + xp);
        }
    }

    public static boolean hasJob(String uuid, Job job) {
        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tbljob WHERE UUID = '" + uuid + "' AND job = '" +
                    job + "'").executeQuery();
            check.next();
            if (check.getInt(1) >= 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static ArrayList<ItemStack> getJobs(Player player) {
        String uuid = player.getUniqueId().toString();
        ArrayList<ItemStack> list = new ArrayList<>();

        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tbljob where uuid = '" + uuid + "'").executeQuery();

            while (resultSet.next()) {
                Job job = Job.valueOf(resultSet.getString("job"));
                int level = (int) (resultSet.getDouble("xp") / 100);

                list.add(Menu.makeItem(switch (job) {

                    case ARCHEOLOGIST -> Material.BRUSH;
                    case MINER -> Material.IRON_PICKAXE;
                    case FARMER -> Material.IRON_HOE;
                    case HUNTER -> Material.IRON_SWORD;
                    case CHEF -> Material.RABBIT_STEW;
                },ChatColor.GOLD + job.toString(), String.valueOf(level)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
