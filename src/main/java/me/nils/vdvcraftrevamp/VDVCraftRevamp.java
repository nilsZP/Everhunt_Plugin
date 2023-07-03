package me.nils.vdvcraftrevamp;

import me.nils.vdvcraftrevamp.commands.ItemCommand;
import me.nils.vdvcraftrevamp.listeners.AbilityListener;
import me.nils.vdvcraftrevamp.listeners.PlayerListener;
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

        loadCommands();
        loadListeners();
        Cooldown.setupCooldown();

    }

    @Override
    public void reloadConfig() {
       loadCommands();
       loadListeners();
        Cooldown.setupCooldown();
    }

    private void loadCommands() {
        Bukkit.getPluginCommand("item").setExecutor(new ItemCommand());
    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new AbilityListener(), this);
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
