package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestData {
    private final String uuid;
    private int number;
    private double completion;
    private boolean done;

    public QuestData(String uuid, int number, double completion, boolean done) {
        this.completion = completion;
        this.number = number;
        this.uuid = uuid;
        this.done = done;

        int tiny = 0;

        if (done) {
            tiny = 1;
        }

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblquest WHERE uuid = '" + uuid + "' AND questnumber = '" + number + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblquest (uuid, questnumber, progress, done) VALUES ('" + uuid + "','" + number + "','" +
                        completion + "','" + tiny + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerQuestData() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblquest").executeQuery();

            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                int number = resultSet.getInt("questnumber");
                double completion = resultSet.getDouble("progress");
                boolean done = resultSet.getBoolean("done");

                new QuestData(uuid,number,completion,done);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setCompletion(String uuid, int number, double value) {
        try {
            Everhunt.getDatabase().run("UPDATE tblquest SET progress = '" + value + "' WHERE uuid = '" + uuid + "' AND questnumber = '" + number + "'").executeUpdate();
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
                new QuestData(uuid, number,0,false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
