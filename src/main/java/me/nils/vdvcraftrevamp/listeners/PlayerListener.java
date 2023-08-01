package me.nils.vdvcraftrevamp.listeners;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.entities.SkeletonKnight;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&a" + player.getName() + " &fhas joined the server!")));
        new BukkitRunnable() {
            @Override
            public void run() {
                List<Block> blocks = getNearbyBlocks(player.getLocation(),6);
                for (int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i).getType() == Material.REDSTONE_BLOCK) {
                        Location loc = blocks.get(i).getLocation();
                        loc.add(0,1,0);
                        new SkeletonKnight(loc);
                    }
                }
            }
        }.runTaskTimer(VDVCraftRevamp.getInstance(),50L,3600L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.quitMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " &fhas left the server!")));
    }

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
}
