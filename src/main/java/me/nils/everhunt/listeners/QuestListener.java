package me.nils.everhunt.listeners;

import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.constants.Job;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.*;
import me.nils.everhunt.entities.Springer;
import me.nils.everhunt.entities.bosses.kings.WolfKing;
import me.nils.everhunt.managers.*;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Check;
import me.nils.everhunt.utils.Menu;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
                            player.sendMessage(Component.text(Chat.color("&eOld Man Dave: &fCan you please kill the springers upstairs?")));
                            Location loc = entity.getLocation();
                            loc.add(0, 5, 0);
                            for (int i = 0; i < 3; i++) {
                                new Springer(loc);
                            }
                            QuestData.setCompletion(uuid, 1, 1);
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 1) < 4 && QuestData.getCompletion(uuid, 1) >= 1) {
                            player.sendMessage(Component.text(Chat.color("&eOld Man Dave: &fWhat are you waiting for go kill them all!")));
                            return;
                        } else {
                            player.getInventory().addItem(WeaponManager.items.get("Snow Shovel").getItemStack());
                            player.sendMessage(Component.text(Chat.color("&eOld Man Dave: &fThanks for killing the springers! Have this.")));
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
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fMy wife divorced me after she said that I will")));
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fNEVER be a successful weapon smith,")));
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fso to prove her wrong I am gonna make the")));
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fBEST SWORD EVER I just need a handle can you give me one?")));

                            QuestData.setCompletion(uuid, 2, 1);
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 2) >= 1 && Check.isHolding(player, "Wooden Bat", ItemType.WEAPON) && QuestData.getCompletion(uuid, 2) < 5) {
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fMay I use that Wooden Bat as a handle?")));
                            QuestData.setCompletion(uuid, 2, 2);
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 2) >= 3 && Check.isHolding(player, "Kings Bone", ItemType.ITEM) && QuestData.getCompletion(uuid, 2) < 5) {
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fI can work with this.")));
                            player.getInventory().remove(player.getInventory().getItemInMainHand());
                            QuestData.setCompletion(uuid, 2, 5);
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fTalk to me in one second.")));
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 2) < 5 && QuestData.getCompletion(uuid, 2) >= 3) {
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fI recommend looking outside the village for materials.")));
                        } else {
                            player.getInventory().addItem(WeaponManager.items.get("Lucia I").getItemStack());
                            player.sendMessage(Component.text(Chat.color("&eMarcus: &fThanks for helping me! Here have the blade and talk to me later!")));
                            QuestData.setDone(uuid, 2);
                            PlayerData.data.get(uuid).addXp(125);
                        }
                    }
                }
                if (data.getDisplayName().equals("Mikull")) {
                    if (!(QuestData.getDone(uuid, 2)) && QuestData.getCompletion(uuid, 2) >= 1) {
                        player.sendMessage(Component.text(Chat.color("&eMikull: &fCan you pls help my brother kill a wolf?")));
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
                            player.sendMessage(Component.text(Chat.color("&eMi: &fSo you are the hunter my brother sent to me?")));
                            player.sendMessage(Component.text(Chat.color("&eMi: &fWell you'll just have to do then!")));
                            List<String> entityNameList = new ArrayList<>();
                            for (Entity entity1 : player.getNearbyEntities(8, 8, 8)) {
                                entityNameList.add(entity1.getName());
                            }
                            if (!entityNameList.contains("Wolf King")) {
                                new WolfKing(new Location(player.getWorld(), 0, -58, 216));
                            }
                        } else {
                            player.sendMessage(Component.text(Chat.color("&eMi: &fWell done hunter!")));
                            player.sendMessage(Component.text(Chat.color("&eMi: &fMaybe you're not as weak as you look.")));
                            player.sendMessage(Component.text(Chat.color("&fUse the /warp command if you wish to return to the village in the sky.")));
                            return;
                        }
                    }
                    if (!(QuestData.getDone(uuid, 3)) && QuestData.getDone(uuid,2)) {
                        if (QuestData.getCompletion(uuid,3) == 6) {
                            player.sendMessage(Component.text(Chat.color("&eMi: &fPlease, go kill some Undead Scarecrows!")));
                            player.sendMessage(Component.text(Chat.color("&eMi: &fI would really appreciate it.")));
                            QuestData.setCompletion(uuid,3,7);
                            // TODO add spawning mechanic for undead scarecrows
                        }
                        if (QuestData.getCompletion(uuid,3) == 8) {
                            player.sendMessage(Component.text(Chat.color("&eMi: &fThanks.")));
                            player.getInventory().addItem(HelmetManager.items.get("Jester Helmet").getItemStack());
                            QuestData.setCompletion(uuid,3,9);
                        }
                    }
                }
                if (data.getDisplayName().equals("Hunter")) {
                    if (!(QuestData.getDone(uuid, 3)) && QuestData.getDone(uuid,2)) {
                        if (QuestData.getCompletion(uuid, 3) <= 1) {
                            player.sendMessage(Component.text(Chat.color("&eHunter: &fAre you the hunter who killed the Wolf King?")));
                            player.sendMessage(Component.text(Chat.color("&eHunter: &fThat's amazing can I have your armor?")));
                            player.sendMessage(Component.text(Chat.color("&fType yes to give him your farmers armor.")));
                            QuestData.setCompletion(uuid, 3, 1);
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 3) == 2 && Check.isHolding(player, "Wheat", ItemType.ITEM, 30)) {
                            player.sendMessage(Component.text(Chat.color("&eHunter: &fThanks!")));
                            player.sendMessage(Component.text(Chat.color("&eHunter: &fHere have these Jester boots!")));
                            player.getInventory().addItem(ArmorManager.items.get("Jester Boots").getItemStack());
                            // TODO add warp to hunters guild
                            QuestData.setCompletion(uuid, 3, 3);
                            return;
                        } else {
                            player.sendMessage(Component.text(Chat.color("&eHunter: &fAre you gonna give me the wheat?")));
                            player.sendMessage(Component.text(Chat.color("&fTalk to the hunter holding 30 wheat.")));
                        }
                        if (QuestData.getCompletion(uuid,3) == 4) {
                            player.sendMessage(Component.text(Chat.color("&eHunter: &fWant to buy the Jester Leggings for 100 coins?")));
                            Menu.createSellMenu(player,ArmorManager.items.get("Jester Leggings").getItemStack());
                            QuestData.setCompletion(uuid,3,5);
                        }
                        if (QuestData.getCompletion(uuid,3) == 5 && player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() >= 5) {
                            player.sendMessage(Component.text(Chat.color("&eHunter: &fWow, you look pretty strong!")));
                            player.sendMessage(Component.text(Chat.color("&eHunter: &fHere, have this!")));
                            player.getInventory().addItem(ArmorManager.items.get("Jester Leggings").getItemStack());
                            QuestData.setCompletion(uuid,3,6);
                        }
                    }
                }
                if (data.getDisplayName().equals("Guild Master")) {
                    if (!(QuestData.getDone(uuid,3)) && QuestData.getDone(uuid,2)) {
                        if (QuestData.getCompletion(uuid,3) == 3) {
                            player.sendMessage(Component.text(Chat.color("&eGuild Master: &fHello, I am the guild master!")));
                            player.sendMessage(Component.text(Chat.color("&eGuild Master: &fI see that you are gathering the pieces for the Jester set!")));
                            player.sendMessage(Component.text(Chat.color("&eGuild Master: &fIf you talk to me when you have the complete set, I'll officially make you a hunter!")));
                            player.sendMessage(Component.text(Chat.color("&fLook around the guild house and see if you can get another piece.")));
                            QuestData.setCompletion(uuid,3,4);
                        }
                        if (QuestData.getCompletion(uuid,3) == 9) {
                            player.sendMessage(Component.text(Chat.color("&eGuild Master: &fYou are now officially a Monster Hunter!")));
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
                player.sendMessage(Component.text(Chat.color("&eMarcus: &fThank you for the handle!")));
                PlayerData.data.get(uuid).addXp(25);
                player.getInventory().remove(WeaponManager.items.get("Wooden Bat").getItemStack());
                player.sendMessage(Component.text(Chat.color("&eMarcus: &fCan you please go fetch me something to make the blade?")));
                QuestData.setCompletion(uuid, 2, 3);
                return;
            }
        }
        if (QuestData.getCompletion(uuid, 3) == 1) {
            if (event.getMessage().equals("yes")) {
                player.getInventory().remove(ArmorManager.items.get("Farmers Shirt").getItemStack());
                player.getInventory().remove(ArmorManager.items.get("Farmers Pants").getItemStack());
                player.getInventory().remove(ArmorManager.items.get("Farmers Boots").getItemStack());
                player.sendMessage(Component.text(Chat.color("&eHunter: &fThank you for the armor!")));
                player.sendMessage(Component.text(Chat.color("&eHunter: &fI'll do you a favor and give you the opportunity-")));
                player.sendMessage(Component.text(Chat.color("&eHunter: &fTo get some special boots.")));
                player.getInventory().addItem(ToolManager.items.get("Wheat Hoe").getItemStack());
                player.sendMessage(Component.text(Chat.color("&eHunter: &fHave this!")));
                player.sendMessage(Component.text(Chat.color("&eHunter: &fCome back with 30 wheat")));
                player.sendMessage(Component.text(Chat.color("&eHunter: &fand I'll give you the special boots.")));
                QuestData.setCompletion(uuid, 3, 2);
                return;
            }
        }
    }
}
