package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.managers.WeaponManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QuestData {
    private final int playerID;
    private int number;
    private double completion;
    private boolean done;

    public QuestData(int playerID, int number, double completion, boolean done) {
        this.completion = completion;
        this.number = number;
        this.playerID = playerID;
        this.done = done;

        try {
            Everhunt.getDatabase().run("INSERT INTO tblquest (playerID, questnumber, type, done) VALUES ('" + playerID + "','" + number + "','" +
                    completion + "','" + done + "')").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerQuestData() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblquest").executeQuery();

            while (resultSet.next()) {
                int playerID = resultSet.getInt("playerID");
                int number = resultSet.getInt("questnumber");
                double completion = resultSet.getDouble("progress");
                boolean done = resultSet.getBoolean("done");

                ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblquest WHERE playerID = '" + playerID + "' AND questnumber = '" + number + "'").executeQuery();
                check.next();
                if (check.getInt(1) < 1) {
                    new QuestData(playerID,number,completion,done);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setCompletion(int playerID, int number, double value) {
        try {
            Everhunt.getDatabase().run("UPDATE tblquest SET progress = '" + value + "' WHERE playerID = '" + playerID + "' AND questnumber = '" + number + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getCompletion(int playerID, int number) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT completion FROM tblquest WHERE playerID = '" + playerID + "' AND questnumber = '" + number + "'").executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setDone(int playerID, int number) {
        try {
            Everhunt.getDatabase().run("UPDATE tblquest SET done = '" + true + "' WHERE playerID = '" + playerID + "' AND questnumber = '" + number + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getDone(int playerID, int number) {
        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblquest WHERE playerID = '" + playerID + "' AND questnumber = '" + number + "'").executeQuery();
            check.next();
            if (check.getInt(1) > 0) {
                try {
                    ResultSet resultSet = Everhunt.getDatabase().run("SELECT done FROM tblquest WHERE playerID = '" + playerID + "' AND questnumber = '" + number + "'").executeQuery();
                    resultSet.next();
                    return resultSet.getBoolean(1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                new QuestData(playerID, number,0,false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
