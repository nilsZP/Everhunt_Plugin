package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestData {
    public QuestData(String uuid, int number, double completion, String task, boolean done) {
        int tiny = 0;

        if (done) {
            tiny = 1;
        }

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblquest WHERE uuid = '" + uuid + "' AND questnumber = '" + number + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblquest (uuid, questnumber, progress,task, done) VALUES ('" + uuid + "','" + number + "','" +
                        completion + "','" + task + "','" + tiny + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCompletion(Player player, int number, double value, String task) {
        String uuid = player.getUniqueId().toString();
        try {
            Everhunt.getDatabase().run("UPDATE tblquest SET progress = '" + value + "' AND task = '" + task + "' WHERE uuid = '" + uuid + "' AND questnumber = '" + number + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static double getCompletion(String uuid, int number) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT progress FROM tblquest WHERE uuid = '" + uuid + "' AND questnumber = '" + number + "'").executeQuery();
            resultSet.next();
            return resultSet.getDouble(1);
        } catch (SQLException e) {
            return -1;
        }
    }

    public static void setDone(String uuid, int number) {
        try {
            Everhunt.getDatabase().run("UPDATE tblquest SET done = '" + 1 + "' WHERE uuid = '" + uuid + "' AND questnumber = '" + number + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getDone(String uuid, int number) {
        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblquest WHERE uuid = '" + uuid + "' AND questnumber = '" + number + "'").executeQuery();
            check.next();
            if (check.getInt(1) > 0) {
                try {
                    ResultSet resultSet = Everhunt.getDatabase().run("SELECT done FROM tblquest WHERE uuid = '" + uuid + "' AND questnumber = '" + number + "'").executeQuery();
                    resultSet.next();
                    return resultSet.getBoolean(1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                new QuestData(uuid, number,0,"",false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean hasOngoing(String uuid) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblquest WHERE uuid = '" + uuid + "'").executeQuery();

            while (resultSet.next()) {
                boolean done = resultSet.getBoolean("done");

                if (!done) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getCurrentTask(String uuid) {
        if (hasOngoing(uuid)) {
            try {
                ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblquest WHERE uuid = '" + uuid + "'").executeQuery();

                while (resultSet.next()) {
                    String task = resultSet.getString("task");
                    boolean done = resultSet.getBoolean("done");

                    if (!done) {
                        return task;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "No ongoing quest";
    }
}
