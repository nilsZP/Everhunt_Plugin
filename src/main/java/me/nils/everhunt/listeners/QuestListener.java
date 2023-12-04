package me.nils.everhunt.listeners;

import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.data.PlayerData;
import me.nils.everhunt.data.QuestData;
import me.nils.everhunt.data.TeleportData;
import me.nils.everhunt.entities.Springer;
import me.nils.everhunt.entities.Wolfling;
import me.nils.everhunt.entities.bosses.kings.WolfKing;
import me.nils.everhunt.managers.ArmorManager;
import me.nils.everhunt.managers.ItemManager;
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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class QuestListener implements Listener {
    @EventHandler
    public void onNPCInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        Entity entity = event.getRightClicked();
        EntityData data = EntityData.data.get(ChatColor.stripColor(entity.getName()));
        if (data != null) {
            if (data.getType().equals(MobType.NPC)) {
                if (data.getDisplayName().equals("Old Man Dave")) {
                    if (!(QuestData.getDone(uuid,1))) {
                        if (QuestData.getCompletion(uuid,1) == 0) {
                            player.getInventory().addItem(WeaponManager.items.get("Wooden Bat").getItemStack());
                            player.sendMessage(Component.text(Chat.color("&eOld Man Dave: &fCan you please kill the springers upstairs?")));
                            Location loc = entity.getLocation();
                            loc.add(0, 5, 0);
                            for (int i = 0; i < 3; i++) {
                                new Springer(loc);
                            }
                            QuestData.setCompletion(uuid,1,1);
                            return;
                        }
                        if (QuestData.getCompletion(uuid,1) < 4 && QuestData.getCompletion(uuid,1) >= 1) {
                            player.sendMessage(Component.text(Chat.color("&eOld Man Dave: &fWhat are you waiting for go kill them all!")));
                            return;
                        } else {
                            player.getInventory().addItem(WeaponManager.items.get("Snow Shovel").getItemStack());
                            player.sendMessage(Component.text(Chat.color("&eOld Man Dave: &fThanks for killing the springers! Have this.")));
                            player.teleport(new Location(player.getWorld(), 60, -7, -197));
                            new TeleportData(uuid,"Village",60,-7,-197);
                            QuestData.setDone(uuid,1);
                            PlayerData.data.get(uuid).addXp(100);
                        }
                    }
                }
                if (data.getDisplayName().equals("Marcus")) {
                    if (!(QuestData.getDone(uuid,2))) {
                        if (QuestData.getCompletion(uuid,2) == 0) {
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fMy wife divorced me after she said that I will")));
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fNEVER be a successful weaponsmith,")));
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fso to prove her wrong I am gonna make the")));
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fBEST SWORD EVER I just need a handle can you give me one?")));

                            QuestData.setCompletion(uuid,2,1);
                            return;
                        }
                        if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
                            WeaponManager bat = WeaponManager.items.get("Wooden Bat");
                            WeaponManager holding;
                            if ((WeaponManager.items.get(ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())) != null)) {
                                holding = WeaponManager.items.get(ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()));
                            } else {
                                holding = null;
                            }
                            if (QuestData.getCompletion(uuid,2) >= 1 && bat == holding && QuestData.getCompletion(uuid,2) < 5) {
                                player.sendMessage(Component.text(Chat.color("&eMarcus: &fMay I use that Wooden Bat as a handle?")));
                                QuestData.setCompletion(uuid,2,2);
                                return;
                            }
                            ItemManager item = ItemManager.items.get("Kings Bone");
                            ItemManager heldItem;
                            if ((ItemManager.items.get(ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())) != null)) {
                                heldItem = ItemManager.items.get(ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()));
                            } else {
                                heldItem = null;
                            }
                            if (QuestData.getCompletion(uuid,2) >= 3 && heldItem == item && QuestData.getCompletion(uuid,2) < 5) {
                                player.sendMessage(Component.text(Chat.color("&eMarcus: &fI can work with this.")));
                                player.getInventory().remove(player.getInventory().getItemInMainHand());
                                QuestData.setCompletion(uuid,2,5);
                                player.sendMessage(Component.text(Chat.color("&eMarcus: &fTalk to me in one second.")));
                                return;
                            }
                        }
                        if (QuestData.getCompletion(uuid,2) < 5 && QuestData.getCompletion(uuid,2) >= 3) {
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fI recommend looking outside the village for materials.")));
                        } else {
                            player.getInventory().addItem(WeaponManager.items.get("Lucia I").getItemStack());
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fThanks for helping me! Here have the blade and talk to me later!")));
                            QuestData.setDone(uuid,2);
                            PlayerData.data.get(uuid).addXp(125);
                        }
                    }
                }
                if  (data.getDisplayName().equals("Mikull")) {
                    if (!(QuestData.getDone(uuid,2)) && QuestData.getCompletion(uuid,2) >= 1) {
                        player.sendMessage(Component.text(Chat.color("&eMikull: &fCan you pls help my brother kill a wolf?")));
                        player.getInventory().addItem(ArmorManager.items.get("Straw Hat").getItemStack());
                        player.getInventory().addItem(ArmorManager.items.get("Farmers Shirt").getItemStack());
                        player.getInventory().addItem(ArmorManager.items.get("Farmers Pants").getItemStack());
                        player.getInventory().addItem(ArmorManager.items.get("Farmers Boots").getItemStack());
                        player.teleport(new Location(player.getWorld(), -1, -57, 199));
                        new TeleportData(uuid,"Farm",-1,-57,199);
                    }
                }
                if (data.getDisplayName().equals("Mi")) {
                    if (!(QuestData.getDone(uuid,2))) {
                        if (QuestData.getCompletion(uuid,2) >= 1 && QuestData.getCompletion(uuid,2) < 5) {
                            player.sendMessage(Component.text(Chat.color("&eMi: &fSo you are the hunter my brother sent to me?")));
                            player.sendMessage(Component.text(Chat.color("&eMi: &fWell you'll just have to do then!")));
                            List<String> entityNameList = new ArrayList<>();
                            for (Entity entity1 : player.getNearbyEntities(8,8,8)) {
                                entityNameList.add(entity1.getName());
                            }
                            if (!entityNameList.contains("Wolf King")) {
                                new WolfKing(new Location(player.getWorld(),0,-58,216));
                            }
                        } else {
                            player.sendMessage(Component.text(Chat.color("&eMi: &fWell done hunter!")));
                            player.sendMessage(Component.text(Chat.color("&eMi: &fMaybe you're not as weak as you look.")));
                            player.sendMessage(Component.text(Chat.color("&fUse the /warp command if you wish to return to the village in the sky.")));
                            return;
                        }
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
            String uuid = player.getUniqueId().toString();
            if (entity.getName().equals("Springer")) {
                if (!(QuestData.getDone(uuid,1)) && QuestData.getCompletion(uuid,1) >= 1) {
                    double completion = QuestData.getCompletion(uuid,1) + 1;
                    QuestData.setCompletion(uuid,1,completion);
                }
            }
            if (entity.getName().equals("Wolf King")) {
                if (!(QuestData.getDone(uuid,2)) && QuestData.getCompletion(uuid,2) >= 1) {
                    double completion = QuestData.getCompletion(uuid,2) + 2;
                    QuestData.setCompletion(uuid,2,completion);
                    PlayerData.data.get(uuid).addXp(50);
                }
            }
        }
    }

    @EventHandler
    public void onMessage(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        if (QuestData.getCompletion(uuid,2) == 2) {
            if (event.getMessage().equals("yes")) {
                player.sendMessage(Component.text(Chat.color("&eMarcus: &fThank you for the handle!")));
                PlayerData.data.get(uuid).addXp(25);
                player.getInventory().remove(WeaponManager.items.get("Wooden Bat").getItemStack());
                player.sendMessage(Component.text(Chat.color("&eMarcus: &fCan you please go fetch me something to make the blade?")));
                QuestData.setCompletion(uuid,2,3);
            }
        }
    }
}
