package me.nils.everhunt;

import me.nils.everhunt.commands.ItemCommand;
import me.nils.everhunt.commands.SpawnCommand;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.data.QuestData;
import me.nils.everhunt.listeners.AbilityListener;
import me.nils.everhunt.listeners.EntityListener;
import me.nils.everhunt.listeners.PlayerListener;
import me.nils.everhunt.listeners.QuestListener;
import me.nils.everhunt.managers.ArmorManager;
import me.nils.everhunt.managers.WeaponManager;
import me.nils.everhunt.utils.Cooldown;
import me.nils.everhunt.utils.Database;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Everhunt extends JavaPlugin {

    public static Everhunt instance;
    public static NamespacedKey key;
    private static Database database;

    @Override
    public void onEnable() {
        instance = this;
        key = new NamespacedKey(this, "key");
        database = new Database();

        register();
    }

    @Override
    public void reloadConfig() {
        register();
    }

    private void loadCommands() {
        Bukkit.getPluginCommand("item").setExecutor(new ItemCommand());
        Bukkit.getPluginCommand("spawn").setExecutor(new SpawnCommand());
    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new AbilityListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new QuestListener(), this);
    }

    private void register() {
        loadCommands();
        loadListeners();
        Cooldown.setupCooldown();
        WeaponManager.registerItems();
        ArmorManager.registerItems();
        EntityData.registerEntities();
        QuestData.registerQuestData();
        PlayerData.registerPlayerData();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Component.text("Shutting down..."));
    }

    public static Everhunt getInstance() {
        return instance;
    }

    public static NamespacedKey getKey() {
        return key;
    }

    public static Database getDatabase() {
        return database;
    }
}