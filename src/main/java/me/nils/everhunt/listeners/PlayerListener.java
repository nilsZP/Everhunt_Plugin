package me.nils.everhunt.listeners;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.data.FlowData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.entities.UndeadScarecrow;
import me.nils.everhunt.managers.ItemManager;
import me.nils.everhunt.managers.ToolManager;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&a" + player.getName() + " &fhas joined the server!")));
        new PlayerData(player.getUniqueId().toString(),player.getName(),0,0);
        int level = PlayerData.data.get(player.getUniqueId().toString()).getXp() / 100;
        player.setPlayerListName(String.format("[%d] %s",level,player.getName()));
        new FlowData(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.quitMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " &fhas left the server!")));
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            event.setCancelled(true);
            player.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) throws MalformedURLException {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        String toolName;
        try {
            toolName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        } catch (NullPointerException e) {
            event.setCancelled(true);
            return;
        }
        ToolManager tool = ToolManager.items.get(ChatColor.stripColor(toolName));

        if (tool != null) {
            Ability ability = tool.getAbility();
            if (event.getBlock().getType() == Material.WHEAT) {
                if (ability == Ability.BREAD_MAKER) {
                    event.getBlock().getDrops().clear();
                    for (int i = 0; i < 3; i++) {
                        player.getWorld().dropItemNaturally(event.getBlock().getLocation(), ItemManager.items.get("Wheat").getItemStack());
                    }
                    event.getBlock().setType(Material.AIR);
                    return;
                }
            }
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onXpGain(PlayerPickupExperienceEvent event) {
        event.getExperienceOrb().remove();
        event.setCancelled(true);
    }

    public static void checkMonsterSpawn() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.getWorld().isDayTime()) {
                        List<Block> blocks = getNearbyBlocks(player.getLocation(),8);
                        for (Block block : blocks) {
                            Location loc = block.getLocation();
                            Block block1 = loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY() + 1, loc.getBlockZ());
                            Block block2 = loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY() + 2, loc.getBlockZ());

                            if (block1.isEmpty() && block2.isEmpty()) {
                                Random random = new Random();
                                int randInt = random.nextInt(0,11);

                                if (randInt == 1) {
                                    if (block1.getLightLevel() <= 6) {
                                        if (block.getType() == Material.WHEAT) {
                                            new UndeadScarecrow(loc);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(Everhunt.getInstance(),300L,300L);
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
