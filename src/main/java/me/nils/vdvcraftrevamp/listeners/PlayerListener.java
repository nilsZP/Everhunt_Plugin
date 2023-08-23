package me.nils.vdvcraftrevamp.listeners;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import io.papermc.paper.event.player.PlayerTradeEvent;
import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.constants.MobType;
import me.nils.vdvcraftrevamp.data.EntityData;
import me.nils.vdvcraftrevamp.entities.SkeletonKnight;
import me.nils.vdvcraftrevamp.entities.Springer;
import me.nils.vdvcraftrevamp.items.armor.SpringerBoots;
import me.nils.vdvcraftrevamp.items.weapons.LuciaI;
import me.nils.vdvcraftrevamp.items.weapons.SnowShovel;
import me.nils.vdvcraftrevamp.items.weapons.WoodenBat;
import me.nils.vdvcraftrevamp.managers.ArmorManager;
import me.nils.vdvcraftrevamp.managers.WeaponManager;
import me.nils.vdvcraftrevamp.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&a" + player.getName() + " &fhas joined the server!")));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,999999999,254,false,false,false));
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

    @EventHandler
    public void onRespawn(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().contains(ArmorManager.items.get("Springer Boots").getItemStack())) {
            player.teleport(new Location(player.getWorld(), 411, -58, 8));
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,999999999,254,false,false,false));
        }
    }

    @EventHandler
    public void onNPCInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        EntityData data = EntityData.entities.get(ChatColor.stripColor(entity.getName()));
        if (data != null) {
            if (data.getType().equals(MobType.NPC)) {
                if (data.getDisplayName().equals("Old Man Dave")) {
                    if (!(player.getInventory().contains(WeaponManager.items.get("Wooden Bat").getItemStack()))) {
                        player.getInventory().addItem(new WoodenBat().getItemStack());
                        player.sendMessage(Component.text(Chat.color("&fCan you please kill the springers upstairs?")));
                    }
                    if (!(player.getInventory().contains(ArmorManager.items.get("Springer Boots").getItemStack()))) {
                        Location loc = entity.getLocation();
                        loc.add(0,5,0);
                        for (int i = 0; i < 3; i++) {
                            new Springer(loc);
                        }
                    } else {
                        if (!(player.getInventory().contains(WeaponManager.items.get("Snow Shovel").getItemStack()))) {
                            player.getInventory().addItem(new SnowShovel().getItemStack());
                            player.sendMessage(Component.text(Chat.color("&fThanks for killing the springers! Have this.")));
                            player.teleport(new Location(player.getWorld(), 411, -58, 8));
                        }
                    }
                }
                if (data.getDisplayName().equals("Marcus")) {
                    if (!(player.getInventory().contains(WeaponManager.items.get("Lucia I").getItemStack())) &&
                            !(player.getInventory().getItemInMainHand().equals(WeaponManager.items.get("Wooden Bat").getItemStack()))) {
                        player.sendMessage(Component.text(Chat.color("&fMy wife divorced me after she said that I will")));
                        player.sendMessage(Component.text(Chat.color("&4NEVER &fbe a successful weaponsmith,")));
                        player.sendMessage(Component.text(Chat.color("&fso to prove her wrong I am gonna make the")));
                        player.sendMessage(Component.text(Chat.color("&eBEST SWORD EVER &fI just need a handle can you give me one?")));
                    }
                    if (player.getInventory().contains(WeaponManager.items.get("Wooden Bat").getItemStack())) {
                        player.sendMessage(Component.text(Chat.color("&fMay I use that Wooden Bat as a handle?")));
                    }
                    if (player.getInventory().getItemInMainHand().equals(WeaponManager.items.get("Wooden Bat").getItemStack())) {
                        player.sendMessage(Component.text(Chat.color("&fThank you for the handle here take my blade.")));
                        player.getInventory().remove(WeaponManager.items.get("Wooden Bat").getItemStack());
                        player.getInventory().addItem(new LuciaI().getItemStack());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTrade(InventoryOpenEvent event) {
        if ((event.getPlayer() instanceof Player) && (event.getInventory().getType() == InventoryType.MERCHANT)) {
            event.setCancelled(true);
        }
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