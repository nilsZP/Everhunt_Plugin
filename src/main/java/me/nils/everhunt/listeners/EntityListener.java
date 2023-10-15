package me.nils.everhunt.listeners;

import me.nils.everhunt.items.armor.MechanicalChestplate;
import me.nils.everhunt.items.armor.SpringerBoots;
import me.nils.everhunt.items.armor.UnitedHelmet;
import me.nils.everhunt.items.weapons.AzureWrath;
import me.nils.everhunt.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class EntityListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            Entity entity = event.getEntity();
            Random rand = new Random();
            if (entity.getName().equals("Thunder Bones")) {
                double randomNumber = rand.nextDouble(101);
                if (randomNumber >= 80) {
                    ItemStack drop = new AzureWrath().getItemStack();
                    entity.getWorld().dropItemNaturally(entity.getLocation(), drop);
                    Bukkit.broadcast(Component.text(Chat.color("&eRARE DROP " + drop.getItemMeta().getDisplayName())));
                }
                if (randomNumber >= 99.9) {
                    ItemStack drop = new UnitedHelmet().getItemStack();
                    entity.getWorld().dropItemNaturally(entity.getLocation(), drop);
                    Bukkit.broadcast(Component.text(Chat.color("&eRARE DROP " + drop.getItemMeta().getDisplayName())));
                }
            }
            if (entity.getName().equals("Springer")) {
                double randomNumber = rand.nextInt(3);
                if (randomNumber == 2) {
                    ItemStack drop = new SpringerBoots().getItemStack();
                    entity.getWorld().dropItemNaturally(entity.getLocation(), drop);
                    Bukkit.broadcast(Component.text(Chat.color("&eRARE DROP " + drop.getItemMeta().getDisplayName())));
                }
            }
            if (entity.getName().equals("Mechanical Zombie")) {
                double randomNumber = rand.nextInt(10);
                if (randomNumber == 9) {
                    ItemStack drop = new MechanicalChestplate().getItemStack();
                    entity.getWorld().dropItemNaturally(entity.getLocation(), drop);
                    Bukkit.broadcast(Component.text(Chat.color("&eRARE DROP " + drop.getItemMeta().getDisplayName())));
                }
            }
 /*           PersistentDataContainer pdc = entity.getPersistentDataContainer();
            if (pdc.has(Everhunt.getKey())) {
                if (Objects.equals(pdc.get(Everhunt.getKey(), PersistentDataType.STRING), "boss")) {
                    Random rand = new Random();
                    int randomNumber = rand.nextInt(101);
                    if (randomNumber >= 80) {
                        if (randomNumber%2 == 0) {
                            ItemStack drop = new Materials().UnisonShard();
                            entity.getWorld().dropItemNaturally(entity.getLocation(),drop);
                            Bukkit.broadcast(Component.text(Chat.color("&eRARE DROP " + drop.getItemMeta().getDisplayName())));
                        } else {
                            ItemStack drop = new Materials().ShatteredShard();
                            entity.getWorld().dropItemNaturally(entity.getLocation(),drop);
                            Bukkit.broadcast(Component.text(Chat.color("&eRARE DROP " + drop.getItemMeta().getDisplayName())));
                        }
                    }
                }
            }*/
        }
    }
}
