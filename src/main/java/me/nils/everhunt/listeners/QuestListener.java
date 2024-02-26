package me.nils.everhunt.listeners;

import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.constants.Job;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.*;
import me.nils.everhunt.entities.Springer;
import me.nils.everhunt.entities.bosses.kings.WolfKing;
import me.nils.everhunt.managers.*;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Condition;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class QuestListener implements Listener {
    @EventHandler
    public void onNPCInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        PlayerData playerData = PlayerData.data.get(uuid);
        Entity entity = event.getRightClicked();
        EntityData data = EntityData.data.get(ChatColor.stripColor(entity.getName()));
        if (data != null) {
            if (data.getType().equals(MobType.NPC)) {
                if (data.getDisplayName().equals("Old Man Dave")) {
                    if (!(QuestData.getDone(uuid, 1))) {
                        if (QuestData.getCompletion(uuid, 1) == 0) {
                            player.getInventory().addItem(WeaponManager.items.get("Wooden Bat").getItemStack());
                            Chat.npc(player,"Old Man Dave","Can you please kill the springers upstairs?");
                            Location loc = entity.getLocation();
                            loc.add(0, 5, 0);
                            for (int i = 0; i < 3; i++) {
                                new Springer(loc);
                            }
                            QuestData.setCompletion(uuid, 1, 1);
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 1) < 4 && QuestData.getCompletion(uuid, 1) >= 1) {
                            Chat.npc(player,"Old Man Dave","What are you waiting for go kill them all!");
                            return;
                        } else {
                            player.getInventory().addItem(WeaponManager.items.get("Snow Shovel").getItemStack());
                            Chat.npc(player,"Old Man Dave","Thanks for killing the springers! Have this.");
                            player.teleport(new Location(player.getWorld(), 60, -7, -197));
                            new TeleportData(uuid, "Village", 60, -7, -197);
                            QuestData.setDone(uuid, 1);
                            PlayerData.data.get(uuid).addXp(100);
                        }
                    }
                }
                if (data.getDisplayName().equals("Marcus")) {
                    if (!(QuestData.getDone(uuid, 2))) {
                        if (QuestData.getCompletion(uuid, 2) == 0) {
                            Chat.npc(player,"Marcus","My wife divorced me after she said that I will");
                            Chat.npc(player,"Marcus","NEVER be a successful weapon smith,");
                            Chat.npc(player,"Marcus","so to prove her wrong I am gonna make the");
                            Chat.npc(player,"Marcus","BEST SWORD EVER I just need a handle can you give me one?");

                            QuestData.setCompletion(uuid, 2, 1);
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 2) >= 1 && Condition.isHolding(player, "Wooden Bat", ItemType.WEAPON) && QuestData.getCompletion(uuid, 2) < 5) {
                            Chat.npc(player,"Marcus","May I use that Wooden Bat as a handle?");
                            QuestData.setCompletion(uuid, 2, 2);
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 2) >= 3 && Condition.isHolding(player, "Kings Bone", ItemType.ITEM) && QuestData.getCompletion(uuid, 2) < 5) {
                            Chat.npc(player,"Marcus","I can work with this.");
                            player.getInventory().remove(player.getInventory().getItemInMainHand());
                            QuestData.setCompletion(uuid, 2, 5);
                            Chat.npc(player,"Marcus","Talk to me in one second.");
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 2) < 5 && QuestData.getCompletion(uuid, 2) >= 3) {
                            Chat.npc(player,"Marcus","I recommend looking outside the village for materials.");
                            Chat.guide(player,"Look for the cat");
                        } else {
                            player.getInventory().addItem(WeaponManager.items.get("Lucia I").getItemStack());
                            Chat.npc(player,"Marcus","Thanks for helping me! Here have the blade and talk to me later!");
                            QuestData.setDone(uuid, 2);
                            PlayerData.data.get(uuid).addXp(125);
                        }
                    }
                }
                if (data.getDisplayName().equals("Mikull")) {
                    if (!(QuestData.getDone(uuid, 2)) && QuestData.getCompletion(uuid, 2) >= 1) {
                        Chat.npc(player,"Mikull","Can you pls help my brother kill a wolf?");
                        player.getInventory().addItem(ArmorManager.items.get("Straw Hat").getItemStack());
                        player.getInventory().addItem(ArmorManager.items.get("Farmers Shirt").getItemStack());
                        player.getInventory().addItem(ArmorManager.items.get("Farmers Pants").getItemStack());
                        player.getInventory().addItem(ArmorManager.items.get("Farmers Boots").getItemStack());
                        player.teleport(new Location(player.getWorld(), -1, -57, 199));
                        new TeleportData(uuid, "Farm", -1, -57, 199);
                    }
                }
                if (data.getDisplayName().equals("Mi")) {
                    if (!(QuestData.getDone(uuid, 2))) {
                        if (QuestData.getCompletion(uuid, 2) >= 1 && QuestData.getCompletion(uuid, 2) < 5) {
                            Chat.npc(player,"Mi","So you are the hunter my brother sent to me?");
                            Chat.npc(player,"Mi","Well you'll just have to do then!");
                            List<String> entityNameList = new ArrayList<>();
                            for (Entity entity1 : player.getNearbyEntities(8, 8, 8)) {
                                entityNameList.add(entity1.getName());
                            }
                            if (!entityNameList.contains("Wolf King")) {
                                new WolfKing(new Location(player.getWorld(), 0, -58, 216));
                            }
                        } else {
                            Chat.npc(player,"Mi","Well done Hunter!");
                            Chat.npc(player,"Mi","Maybe you're not as weak as you look.");
                            Chat.guide(player,"Use the /warp command if you wish to return to the village in the sky.");
                            return;
                        }
                    }
                    if (!(QuestData.getDone(uuid, 3)) && QuestData.getDone(uuid,2)) {
                        if (QuestData.getCompletion(uuid,3) == 6) {
                            Chat.npc(player,"Mi","Please, go kill some Undead Scarecrows!");
                            Chat.npc(player,"Mi","I would really appreciate it.");
                            QuestData.setCompletion(uuid,3,7);
                            // TODO add spawning mechanic for undead scarecrows
                        }
                        if (QuestData.getCompletion(uuid,3) == 8) {
                            Chat.npc(player,"Mi","Thanks.");
                            player.getInventory().addItem(HelmetManager.items.get("Jester Helmet").getItemStack());
                            QuestData.setCompletion(uuid,3,9);
                        }
                    }
                }
                if (data.getDisplayName().equals("Hunter")) {
                    if (!(QuestData.getDone(uuid, 3)) && QuestData.getDone(uuid,2)) {
                        if (QuestData.getCompletion(uuid, 3) <= 1) {
                            Chat.npc(player,"Hunter","Are you the hunter who killed the Wolf King?");
                            Chat.npc(player,"Hunter","That's amazing can I have your armor?");
                            Chat.guide(player,"Type yes to give him your farmers armor.");
                            QuestData.setCompletion(uuid, 3, 1);
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 3) == 2 && Condition.isHolding(player, "Wheat", ItemType.ITEM, 30)) {
                            Chat.npc(player,"Hunter","Thanks!");
                            Chat.npc(player,"Hunter","Here have these Jester boots!");
                            player.getInventory().addItem(ArmorManager.items.get("Jester Boots").getItemStack());
                            // TODO add warp to hunters guild
                            QuestData.setCompletion(uuid, 3, 3);
                            return;
                        } else {
                            Chat.npc(player,"Hunter","Are you gonna give me the wheat?");
                            Chat.guide(player,"Talk to the hunter holding 30 wheat.");
                        }
                        if (QuestData.getCompletion(uuid,3) == 4) {
                            player.sendMessage(Component.text(Chat.color("&eHunter: &fWant to buy the Jester Leggings for 100 coins?")));
                            // TODO add sell menu
                            QuestData.setCompletion(uuid,3,5);
                        }
                        if (QuestData.getCompletion(uuid,3) == 5 && player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() >= 5) {
                            Chat.npc(player,"Hunter","Wow, you look pretty strong!");
                            Chat.npc(player,"Hunter","Here, have this!");
                            player.getInventory().addItem(ArmorManager.items.get("Jester Leggings").getItemStack());
                            QuestData.setCompletion(uuid,3,6);
                        }
                    }
                }
                if (data.getDisplayName().equals("Guild Master")) {
                    if (!(QuestData.getDone(uuid,3)) && QuestData.getDone(uuid,2)) {
                        if (QuestData.getCompletion(uuid,3) == 3) {
                            Chat.npc(player,"Guild Master","Hello, I am the guild master!");
                            Chat.npc(player,"Guild Master","I see that you are gathering the pieces for the Jester set!");
                            Chat.npc(player,"Guild Master","If you talk to me when you have the complete set, I'll officially make you a hunter!");
                            Chat.guide(player,"Look around the guild house and see if you can get another piece.");
                            QuestData.setCompletion(uuid,3,4);
                        }
                        if (QuestData.getCompletion(uuid,3) == 9) {
                            Chat.npc(player,"Guild Master","You are now officially a Monster Hunter!");
                            new JobData(uuid, Job.HUNTER,0);
                            playerData.addXp(235);
                            QuestData.setDone(uuid,3);
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

        if (killer instanceof Player player) {
            String uuid = player.getUniqueId().toString();
            if (entity.getName().equals("Springer")) {
                if (!(QuestData.getDone(uuid, 1)) && QuestData.getCompletion(uuid, 1) >= 1) {
                    double completion = QuestData.getCompletion(uuid, 1) + 1;
                    QuestData.setCompletion(uuid, 1, completion);
                }
            }
            if (entity.getName().equals("Undead Scarecrow")) {
                if (!(QuestData.getDone(uuid,3)) && QuestData.getCompletion(uuid,3) == 7) {
                    QuestData.setCompletion(uuid,3,8);
                }
            }
            if (entity.getName().equals("Wolf King")) {
                if (!(QuestData.getDone(uuid, 2)) && QuestData.getCompletion(uuid, 2) >= 1) {
                    double completion = QuestData.getCompletion(uuid, 2) + 2;
                    QuestData.setCompletion(uuid, 2, completion);
                    PlayerData.data.get(uuid).addXp(50);
                }
            }
        }
    }

    @EventHandler
    public void onMessage(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        if (QuestData.getCompletion(uuid, 2) == 2) {
            if (event.getMessage().equals("yes")) {
                Chat.npc(player,"Marcus","Thank you for the handle!");
                PlayerData.data.get(uuid).addXp(25);
                player.getInventory().remove(WeaponManager.items.get("Wooden Bat").getItemStack());
                Chat.npc(player,"Marcus","Can you please go fetch me something to make the blade?");
                QuestData.setCompletion(uuid, 2, 3);
                return;
            }
        }
        if (QuestData.getCompletion(uuid, 3) == 1) {
            if (event.getMessage().equals("yes")) {
                player.getInventory().remove(ArmorManager.items.get("Farmers Shirt").getItemStack());
                player.getInventory().remove(ArmorManager.items.get("Farmers Pants").getItemStack());
                player.getInventory().remove(ArmorManager.items.get("Farmers Boots").getItemStack());
                Chat.npc(player,"Hunter","Thank you for the armor!");
                Chat.npc(player,"Hunter","I'll do you a favor and give you the opportunity-");
                Chat.npc(player,"Hunter","to get some special boots.");
                player.getInventory().addItem(ToolManager.items.get("Wheat Hoe").getItemStack());
                Chat.npc(player,"Hunter","Have this!");
                Chat.npc(player,"Hunter","Come back with 30 wheat");
                Chat.npc(player,"Hunter","and I'll give you the special boots.");
                QuestData.setCompletion(uuid, 3, 2);
                return;
            }
        }
    }
}
