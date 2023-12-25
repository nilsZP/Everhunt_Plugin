package me.nils.everhunt;

import me.nils.everhunt.commands.ItemCommand;
import me.nils.everhunt.commands.SpawnCommand;
import me.nils.everhunt.commands.WarpCommand;
import me.nils.everhunt.data.*;
import me.nils.everhunt.items.Items;
import me.nils.everhunt.listeners.*;
import me.nils.everhunt.managers.*;
import me.nils.everhunt.utils.Cooldown;
import me.nils.everhunt.utils.Database;
import me.nils.everhunt.utils.Flow;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.MalformedURLException;

public final class Everhunt extends JavaPlugin {

    public static Everhunt instance;
    public static NamespacedKey key;
    public static Items items;
    private static Database database;

    @Override
    public void onEnable() {
        instance = this;
        key = new NamespacedKey(this, "key");
        items = new Items();
        database = new Database();

        try {
            register();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Flow.regenFlow();
    }

    @Override
    public void reloadConfig() {
        try {
            register();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCommands() {
        Bukkit.getPluginCommand("item").setExecutor(new ItemCommand());
        Bukkit.getPluginCommand("spawn").setExecutor(new SpawnCommand());
        Bukkit.getPluginCommand("warp").setExecutor(new WarpCommand());
    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new AbilityListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new QuestListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    private void register() throws MalformedURLException {
        loadCommands();
        loadListeners();
        Cooldown.setupCooldown();
        WeaponManager.registerItems();
        ArmorManager.registerItems();
        HelmetManager.registerItems();
        EntityData.registerEntities();
        QuestData.registerQuestData();
        PlayerData.registerPlayerData();
        ItemManager.registerItems();
        ToolManager.registerItems();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Component.text("Shutting down..."));
        database.disconnect();
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
