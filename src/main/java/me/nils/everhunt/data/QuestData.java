package me.nils.everhunt.data;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class QuestData {
    public static final HashMap<String, QuestData> questdata = new HashMap<>();
    private final String uuid;
    private int number;
    private double completion;
    private final YamlConfiguration configuration;

    public QuestData(String uuid, int number, double completion) {
        this.completion = completion;
        this.number = number;
        this.uuid = uuid;

        FileManager fileManager = new FileManager("questdata", uuid);
        configuration = fileManager.getFile();
        configuration.set("uuid",uuid);
        configuration.set("number", String.valueOf(number));
        configuration.set("completion", String.valueOf(completion));

        fileManager.save();
        questdata.put(uuid, this);
    }

    public static void registerQuestData() {
        List<File> files = FileManager.getFiles("questdata");
        if (files == null) {
            return;
        }
        for (File file : files) {
            YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

            String uuid = fileConfiguration.getString("uuid");
            int number = fileConfiguration.getInt("number");
            double completion = fileConfiguration.getDouble("completion");

            new QuestData(uuid,number,completion);
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(String uuid, int value) {
        File file = FileManager.getFile("questdata",uuid);
        YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        fileConfiguration.set("number", String.valueOf(value));
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.number = value;
    }

    public double getCompletion() {
        return completion;
    }

    public void setCompletion(String uuid, double value) {
        File file = FileManager.getFile("questdata",uuid);
        YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        fileConfiguration.set("completion", String.valueOf(value));
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.completion = value;
    }
}
