package me.nils.vdvcraftrevamp.listeners;

import me.nils.vdvcraftrevamp.VDVCraftRevamp;
import me.nils.vdvcraftrevamp.items.weapons.AzureWrath;
import me.nils.vdvcraftrevamp.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.Random;

public class EntityListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            Entity entity = event.getEntity();
            if (entity.getName().equals("Thunder Bones")) {
                Random rand = new Random();
                int randomNumber = rand.nextInt(101);
                if (randomNumber >= 80) {
                    ItemStack drop = new AzureWrath().getItemStack();
                    entity.getWorld().dropItemNaturally(entity.getLocation(),drop);
                    Bukkit.broadcast(Component.text(Chat.color("&eRARE DROP " + drop.getItemMeta().getDisplayName())));
                }
            }
 /*           PersistentDataContainer pdc = entity.getPersistentDataContainer();
            if (pdc.has(VDVCraftRevamp.getKey())) {
                if (Objects.equals(pdc.get(VDVCraftRevamp.getKey(), PersistentDataType.STRING), "boss")) {
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
