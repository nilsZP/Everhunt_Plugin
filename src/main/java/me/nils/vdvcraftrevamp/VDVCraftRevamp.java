package me.nils.vdvcraftrevamp;

import me.nils.vdvcraftrevamp.commands.ItemCommand;
import me.nils.vdvcraftrevamp.commands.SpawnCommand;
import me.nils.vdvcraftrevamp.data.EntityData;
import me.nils.vdvcraftrevamp.data.QuestData;
import me.nils.vdvcraftrevamp.listeners.AbilityListener;
import me.nils.vdvcraftrevamp.listeners.EntityListener;
import me.nils.vdvcraftrevamp.listeners.PlayerListener;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import me.nils.vdvcraftrevamp.utils.Cooldown;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class VDVCraftRevamp extends JavaPlugin {

    public static VDVCraftRevamp instance;
    public static NamespacedKey key;

    @Override
    public void onEnable() {
        instance = this;
        key = new NamespacedKey(this, "key");

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
    }

    private void register() {
        loadCommands();
        loadListeners();
        Cooldown.setupCooldown();
        WeaponManager.registerItems();
        ArmorManager.registerItems();
        EntityData.registerEntities();
        QuestData.registerQuestData();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Component.text("Shutting down..."));
    }

    public static VDVCraftRevamp getInstance() {
        return instance;
    }

    public static NamespacedKey getKey() {
        return key;
    }
}
