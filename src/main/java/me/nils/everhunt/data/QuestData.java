package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.MobType;
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

    public QuestData(int playerID, int number, double completion) {
        this.completion = completion;
        this.number = number;
        this.playerID = playerID;

        try {
            Everhunt.getDatabase().run("INSERT INTO tblquest (playerID, questnumber, type) VALUES (" + playerID + "," + number + "," +
                    completion).executeQuery();
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

                new QuestData(playerID,number,completion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int playerID, int value) {
        // TODO fix this
    }

    public double getCompletion() {
        return completion;
    }

    public void setCompletion(String uuid, double value) {
        // TODO also this
    }
}
