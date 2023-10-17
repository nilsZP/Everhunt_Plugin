package me.nils.everhunt.listeners;

import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.data.QuestData;
import me.nils.everhunt.entities.Springer;
import me.nils.everhunt.items.weapons.LuciaI;
import me.nils.everhunt.items.weapons.SnowShovel;
import me.nils.everhunt.items.weapons.WoodenBat;
import me.nils.everhunt.managers.WeaponManager;
import me.nils.everhunt.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class QuestListener implements Listener {
    @EventHandler
    public void onNPCInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        Entity entity = event.getRightClicked();
        EntityData data = EntityData.entities.get(ChatColor.stripColor(entity.getName()));
        if (data != null) {
            if (data.getType().equals(MobType.NPC)) {
                if (data.getDisplayName().equals("Old Man Dave")) {
                    if (!(QuestData.getOngoing(playerID,1))) {
                        QuestData.questdata.get(uuid).setNumber(uuid,1);
                        QuestData.questdata.get(uuid).setCompletion(uuid,0);
                        player.getInventory().addItem(new WoodenBat().getItemStack());
                        player.sendMessage(Component.text(Chat.color("&fCan you please kill the springers upstairs?")));
                        Location loc = entity.getLocation();
                        loc.add(0,5,0);
                        for (int i = 0; i < 3; i++) {
                            new Springer(loc);
                        }
                    }
                    if (QuestData.questdata.get(uuid).getCompletion() < 3) {
                        player.sendMessage(Component.text(Chat.color("&fWhat are you waiting for go kill them all!")));
                    } else {
                        player.getInventory().addItem(new SnowShovel().getItemStack());
                        player.sendMessage(Component.text(Chat.color("&fThanks for killing the springers! Have this.")));
                        player.teleport(new Location(player.getWorld(), 411, -58, 8));
                        QuestData.questdata.get(uuid).setNumber(uuid,0);
                        QuestData.questdata.get(uuid).setCompletion(uuid,0);
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

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Entity killer = event.getEntity().getKiller();

        if (killer instanceof Player player){
            Bukkit.broadcast(Component.text("1"));
            if (entity.getName().equals("Springer")) {
                Bukkit.broadcast(Component.text("2"));
                String uuid = player.getUniqueId().toString();
                if (QuestData.questdata.get(uuid).getNumber() == 1) {
                    Bukkit.broadcast(Component.text("3"));
                    double completion = QuestData.questdata.get(uuid).getCompletion() + 1;
                    QuestData.questdata.get(uuid).setCompletion(uuid, completion);
                }
            }
        }
    }
}
