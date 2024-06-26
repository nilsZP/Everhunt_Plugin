package me.nils.everhunt;

import me.nils.everhunt.commands.*;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.data.MarketData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.items.Items;
import me.nils.everhunt.listeners.*;
import me.nils.everhunt.managers.*;
import me.nils.everhunt.menu.MenuListener;
import me.nils.everhunt.menu.PlayerMenuUtility;
import me.nils.everhunt.utils.BrokenBlocksService;
import me.nils.everhunt.utils.Cooldown;
import me.nils.everhunt.utils.Database;
import me.nils.everhunt.utils.Flow;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.MalformedURLException;
import java.util.HashMap;

public final class Everhunt extends JavaPlugin {

    public static Everhunt instance;
    public static NamespacedKey key;
    public static Items items;
    private static Database database;
    public static BrokenBlocksService brokenBlocksService = new BrokenBlocksService();
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

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
        Bukkit.getPluginCommand("market").setExecutor(new MarketCommand());
        Bukkit.getPluginCommand("sell").setExecutor(new SellCommand());
        Bukkit.getPluginCommand("achievement").setExecutor(new AchievementCommand());
        Bukkit.getPluginCommand("guild").setExecutor(new GuildCommand());
        Bukkit.getPluginCommand("report").setExecutor(new ReportCommand());
        Bukkit.getPluginCommand("reports").setExecutor(new ReportsCommand());
        Bukkit.getPluginCommand("job").setExecutor(new JobCommand());
    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new BreakListeners(), this);
        getServer().getPluginManager().registerEvents(new AbilityListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new QuestListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new BackpackListener(), this);
    }

    private void register() throws MalformedURLException {
        loadCommands();
        loadListeners();
        Cooldown.setupCooldown();
        WeaponManager.registerItems();
        ArmorManager.registerItems();
        HelmetManager.registerItems();
        EntityData.registerEntities();
        PlayerData.registerPlayerData();
        ItemManager.registerItems();
        ToolManager.registerItems();
        SoulManager.registerItems();
        DishManager.registerItems();
        MarketData.register();
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

    public static PlayerMenuUtility getPlayerMenuUtility(Player player) {
        PlayerMenuUtility playerMenuUtility;

        if (playerMenuUtilityMap.containsKey(player)) {
            return playerMenuUtilityMap.get(player);
        } else {
            playerMenuUtility = new PlayerMenuUtility(player);
            playerMenuUtilityMap.put(player,playerMenuUtility);

            return playerMenuUtility;
        }
    }
}
