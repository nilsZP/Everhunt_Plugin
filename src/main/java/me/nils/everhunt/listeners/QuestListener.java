package me.nils.everhunt.listeners;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.constants.Job;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.data.*;
import me.nils.everhunt.entities.Springer;
import me.nils.everhunt.entities.bosses.kings.WolfKing;
import me.nils.everhunt.items.Items;
import me.nils.everhunt.managers.ArmorManager;
import me.nils.everhunt.managers.HelmetManager;
import me.nils.everhunt.managers.ToolManager;
import me.nils.everhunt.managers.WeaponManager;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Condition;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class QuestListener implements Listener { // TODO add task text
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
                    if (!(QuestData.getDone(uuid, 1)) ) {
                        if (QuestData.getCompletion(uuid, 1) == 0) {
                            player.getInventory().addItem(WeaponManager.items.get("Wooden Bat").getItemStack());
                            Chat.npc(player,"Old Man Dave","Can you please kill the springers upstairs?");
                            Location loc = entity.getLocation();
                            loc.add(0, 5, 0);
                            for (int i = 0; i < 3; i++) {
                                new Springer(loc);
                            }
                            QuestData.setCompletion(player, 1, 1,"Kill the 3 Springers");
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
                            QuestData.setDone(player, 1);
                            PlayerData.data.get(uuid).addXp(100);
                            return;
                        }
                    }
                }
                if (data.getDisplayName().equals("Marcus")) {
                    if (!(QuestData.getDone(uuid, 2)) ) {
                        if (QuestData.getCompletion(uuid, 2) == 0) {
                            Chat.npc(player,"Marcus","My wife divorced me after she said that I will");
                            Chat.npc(player,"Marcus","NEVER be a successful weapon smith,");
                            Chat.npc(player,"Marcus","so to prove her wrong I am gonna make the");
                            Chat.npc(player,"Marcus","BEST SWORD EVER I just need a handle can you give me one?");

                            QuestData.setCompletion(player, 2, 1,"Talk to Marcus while holding the bat");
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 2) >= 1 && Condition.isHolding(player, "Wooden Bat", ItemType.WEAPON) && QuestData.getCompletion(uuid, 2) < 5) {
                            Chat.npc(player,"Marcus","May I use that Wooden Bat as a handle?");
                            QuestData.setCompletion(player, 2, 2,"Type yes in chat while holding the bat");
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 2) >= 3 && Condition.isHolding(player, "Kings Bone", ItemType.ITEM) && QuestData.getCompletion(uuid, 2) < 5) {
                            Chat.npc(player,"Marcus","I can work with this.");
                            player.getInventory().remove(player.getInventory().getItemInMainHand());
                            QuestData.setCompletion(player, 2, 5,"Talk to Marcus");
                            Chat.npc(player,"Marcus","Talk to me in one second.");
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 2) < 5 && QuestData.getCompletion(uuid, 2) >= 3) {
                            Chat.npc(player,"Marcus","I recommend looking outside the village for materials.");
                            Chat.guide(player,"Look for the cat");
                            return;
                        }
                        if (QuestData.getCompletion(uuid,2) == 5) {
                            player.getInventory().addItem(WeaponManager.items.get("Lucia I").getItemStack());
                            Chat.npc(player,"Marcus","Thanks for helping me! Here have the blade and talk to me later!");
                            QuestData.setDone(player, 2);
                            PlayerData.data.get(uuid).addXp(125);
                            return;
                        }
                    }
                    if (!QuestData.getDone(uuid,5) && QuestData.getDone(uuid,2) ) {
                        if (QuestData.getCompletion(uuid,5) == 0) {
                            Chat.npc(player,"Marcus","I need new materials for Lucia II.");
                            Chat.npc(player,"Marcus","Give me 2 compressed stone pls?");
                            QuestData.setCompletion(player,5,1,"Find a way to collect compressed stone");
                            return;
                        }
                        if (QuestData.getCompletion(uuid,5) == 3 && Condition.isHolding(player,"Compressed Stone",ItemType.ITEM,2)) {
                            Chat.npc(player,"Marcus","Thanks for helping me again!");
                            player.getInventory().remove(player.getInventory().getItemInMainHand());
                            QuestData.setCompletion(player,5,4,"Click him again while holding Lucia I");
                            return;
                        }
                        if (QuestData.getCompletion(uuid,5) == 4 && Condition.isHolding(player,"Lucia I",ItemType.WEAPON)) {
                            player.getInventory().remove(player.getInventory().getItemInMainHand());
                            player.getInventory().addItem(Items.getBase("Lucia II"));
                            QuestData.setDone(player,5);
                            return;
                        }
                    }
                }
                if (data.getDisplayName().equals("Mikull")) {
                    if (!(QuestData.getDone(uuid, 2)) && QuestData.getCompletion(uuid, 2) >= 1 && QuestData.getCompletion(uuid,2) <= 3) {
                        Chat.npc(player,"Mikull","Can you pls help my brother kill a wolf?");
                        player.getInventory().addItem(ArmorManager.items.get("Straw Hat").getItemStack());
                        player.getInventory().addItem(ArmorManager.items.get("Farmers Shirt").getItemStack());
                        player.getInventory().addItem(ArmorManager.items.get("Farmers Pants").getItemStack());
                        player.getInventory().addItem(ArmorManager.items.get("Farmers Boots").getItemStack());
                        player.teleport(new Location(player.getWorld(), -1, -57, 199));
                        new TeleportData(uuid, "Farm", -1, -57, 199);
                        return;
                    }
                }
                if (data.getDisplayName().equals("Mi")) {
                    if (!(QuestData.getDone(uuid, 2))) {
                        if (QuestData.getCompletion(uuid, 2) >= 1 && QuestData.getCompletion(uuid, 2) < 4) {
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
                        }
                        return;
                    }
                    if (!(QuestData.getDone(uuid, 3)) && QuestData.getDone(uuid,2)) {
                        if (QuestData.getCompletion(uuid,3) == 6) {
                            Chat.npc(player,"Mi","Please, go kill a Undead Scarecrow!");
                            Chat.npc(player,"Mi","I would really appreciate it.");
                            QuestData.setCompletion(player,3,7,"Kill 1 Undead Scarecrow");
                            return;
                        }
                        if (QuestData.getCompletion(uuid,3) == 8) {
                            Chat.npc(player,"Mi","Thanks.");
                            player.getInventory().addItem(HelmetManager.items.get("Jester Helmet").getItemStack());
                            QuestData.setCompletion(player,3,9,"Talk to the Guild Master");
                            return;
                        }
                    }
                }
                if (data.getDisplayName().equals("Hunter")) {
                    if (!(QuestData.getDone(uuid, 3)) && QuestData.getDone(uuid,2)) {
                        if (QuestData.getCompletion(uuid, 3) <= 1) {
                            Chat.npc(player,"Hunter","Are you the hunter who killed the Wolf King?");
                            Chat.npc(player,"Hunter","That's amazing can I have your armor?");
                            QuestData.setCompletion(player, 3, 1,"Type yes while wearing the armor");
                            return;
                        }
                        if (QuestData.getCompletion(uuid, 3) == 2 && Condition.isHolding(player, "Wheat", ItemType.ITEM, 30)) {
                            Chat.npc(player,"Hunter","Thanks!");
                            Chat.npc(player,"Hunter","Here have these Jester boots!");
                            int amount = player.getInventory().getItemInMainHand().getAmount();
                            amount -= 30;
                            player.getInventory().getItemInMainHand().setAmount(amount);
                            player.getInventory().addItem(ArmorManager.items.get("Jester Boots").getItemStack());
                            new TeleportData(uuid, "Guild House", 186, -58, -56);
                            QuestData.setCompletion(player, 3, 3,"Go to the Guild House");
                            Chat.guide(player,"Open your warp menu by typing /warp");
                            return;
                        } else if (QuestData.getCompletion(uuid, 3) == 2) {
                            Chat.npc(player,"Hunter","Are you gonna give me the wheat?");
                            Chat.guide(player,"Talk to the hunter while holding 30 wheat.");
                            return;
                        }
                        if (QuestData.getCompletion(uuid,3) == 5 && player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() >= 4) {
                            Chat.npc(player,"Hunter","Wow, you look pretty strong!");
                            Chat.npc(player,"Hunter","Here, have this!");
                            player.getInventory().addItem(ArmorManager.items.get("Jester Chestplate").getItemStack());
                            QuestData.setCompletion(player,3,6,"Look for a way to get the helmet");
                            return;
                        } else if (QuestData.getCompletion(uuid,3) == 4) {
                            Chat.npc(player,"Hunter","Want to buy the Jester Leggings for 100 coins?");
                            if (PlayerData.data.get(uuid).pay(100)) {
                                QuestData.setCompletion(player, 3, 5,"Talk to him again while holding your strongest weapon");
                                player.getInventory().addItem(Items.getBase("Jester Leggings"));
                            } else Chat.guide(player, "To sell items you can use /market to sell to players or you can sell your items with /sell if they have an item value");
                            return;
                        }
                    }
                }
                if (data.getDisplayName().equals("Guild Master")) {
                    if (!(QuestData.getDone(uuid,3)) && QuestData.getDone(uuid,2)) {
                        if (QuestData.getCompletion(uuid,3) == 3) {
                            Chat.npc(player,"Guild Master","Hello, I am the guild master!");
                            Chat.npc(player,"Guild Master","I see that you are gathering the pieces for the Jester set!");
                            Chat.npc(player,"Guild Master","If you talk to me when you have the complete set, I'll officially make you a hunter!");
                            QuestData.setCompletion(player,3,4,"Look around the guild house and see if you can get another piece.");
                            return;
                        }
                        if (QuestData.getCompletion(uuid,3) == 9) {
                            Chat.npc(player,"Guild Master","You are now officially a Monster Hunter!");
                            new JobData(uuid, Job.HUNTER,0);
                            playerData.addXp(235);
                            QuestData.setDone(player,3);
                            return;
                        }
                    }
                }
                if (data.getDisplayName().equals("Farmer")) {
                    if (!QuestData.getDone(uuid,4) && QuestData.getDone(uuid,3) ) {
                        if (Condition.isHolding(player,"Gardening Hoe",ItemType.TOOL) && QuestData.getCompletion(uuid,4) < 1) {
                            Chat.npc(player,"Farmer","Have this backpack now go farm until you have 128 wheat!");
                            Chat.guide(player,"You can open the backpack by holding it and pressing right click on your mouse");
                            player.getInventory().addItem(Items.getBase("Backpack"));
                            QuestData.setCompletion(player,4,1,"Collect 128 wheat");
                            return;
                        } else if (QuestData.getCompletion(uuid,4) < 1) {
                            Chat.guide(player,"Talk to him while holding the gardening hoe");
                        }
                        if (QuestData.getCompletion(uuid,4) == 1 && Condition.has(player,"Wheat",ItemType.ITEM,128)) {
                            Chat.npc(player,"Farmer","Give me your straw hat and i'll upgrade it.");
                            QuestData.setCompletion(player,4,2,"Talk to him again while holding your Straw Hat");
                            return;
                        }
                        if (QuestData.getCompletion(uuid,4) == 2 && Condition.isHolding(player,"Straw Hat",ItemType.ARMOR)) {
                            player.getInventory().remove(player.getInventory().getItemInMainHand());
                            player.getInventory().addItem(Items.getBase("Lucky Straw Hat"));
                            QuestData.setDone(player,4);
                            return;
                        }
                    }
                }
                if (data.getDisplayName().equals("Tim")) {
                    if (!QuestData.getDone(uuid,5) && QuestData.getDone(uuid,2)) {
                        if (QuestData.getCompletion(uuid,5) == 1) {
                            Chat.npc(player,"Tim","Buy my drill for 500 coins!");
                            Chat.npc(player,"Tim","If you want to save my mining business.");
                            if (PlayerData.data.get(uuid).pay(500)) {
                                QuestData.setCompletion(player, 5, 2,"Talk to Tim while holding the drill to proceed");
                                player.getInventory().addItem(Items.getBase("Drill"));
                            } else Chat.guide(player, "To sell items you can use /market to sell to players or you can sell your items with /sell if they have an item value");
                            return;
                        }
                        if (QuestData.getCompletion(uuid,5) == 2 && Condition.isHolding(player,"Drill",ItemType.TOOL)) {
                            new TeleportData(uuid,"Mine",211,-16,187);
                            Chat.guide(player,"use /warp");
                            QuestData.setCompletion(player,5,3,"Go to the Mine");
                            return;
                        }
                    }
                }
                if (data.getDisplayName().equals("Compresser")) {
                    if (player.getInventory().getItemInMainHand().getAmount() != 64) {
                        Chat.guide(player,"Talk to the compresser while holding a stack of items to compress them");
                        Chat.guide(player,"Keep in mind that not everything can be compressed");
                        return;
                    }
                    if (player.getInventory().getItemInMainHand().getType() == Material.STONE || player.getInventory().getItemInMainHand().getType() == Material.COAL
                    || player.getInventory().getItemInMainHand().getType() == Material.IRON_INGOT) {
                        player.getInventory().addItem(switch (player.getInventory().getItemInMainHand().getType()) {
                            case STONE -> Items.getBase("Compressed Stone");
                            case COAL -> Items.getBase("Compressed Coal");
                            case IRON_INGOT -> Items.getBase("Compressed Iron");
                            default -> new ItemStack(Material.AIR);
                        });
                        player.getInventory().getItemInMainHand().setType(Material.AIR);
                    }
                    return;
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
            if (entity.getPersistentDataContainer().has(Everhunt.getKey())) {
                String name = entity.getPersistentDataContainer().get(Everhunt.getKey(), PersistentDataType.STRING);
                if (name.equals("Springer")) {
                    if (!(QuestData.getDone(uuid, 1)) && QuestData.getCompletion(uuid, 1) >= 1) {
                        double completion = QuestData.getCompletion(uuid, 1) + 1;
                        QuestData.setCompletion(player, 1, completion, "Kill all the Springers");
                    }
                }
                if (name.equals("Undead Scarecrow")) {
                    if (!(QuestData.getDone(uuid, 3)) && QuestData.getCompletion(uuid, 3) == 7) {
                        QuestData.setCompletion(player, 3, 8, "Talk to Mi");
                    }
                }
                if (name.equals("Wolf King")) {
                    if (!(QuestData.getDone(uuid, 2)) && QuestData.getCompletion(uuid, 2) >= 1) {
                        double completion = QuestData.getCompletion(uuid, 2) + 2;
                        if (completion > 4) QuestData.setCompletion(player, 2, 4, "Talk to Mi");
                        PlayerData.data.get(uuid).addXp(50);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMessage(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        if (QuestData.getCompletion(uuid, 2) == 2) {
            if (event.getMessage().equals("yes") && Condition.isHolding(player,"Wooden Bat",ItemType.WEAPON)) {
                Chat.npc(player,"Marcus","Thank you for the handle!");
                PlayerData.data.get(uuid).addXp(25);
                player.getInventory().remove(player.getInventory().getItemInMainHand());
                Chat.npc(player,"Marcus","Can you please go fetch me something to make the blade?");
                QuestData.setCompletion(player, 2, 3,"Look for the Kings Bone");
                return;
            }
        }
        if (QuestData.getCompletion(uuid, 3) == 1) {
            if (event.getMessage().equals("yes")) {
                player.getInventory().setBoots(new ItemStack(Material.AIR));
                player.getInventory().setLeggings(new ItemStack(Material.AIR));
                player.getInventory().setChestplate(new ItemStack(Material.AIR));
                Chat.npc(player,"Hunter","Thank you for the armor!");
                Chat.npc(player,"Hunter","I'll do you a favor and give you the opportunity-");
                Chat.npc(player,"Hunter","to get some special boots.");
                player.getInventory().addItem(ToolManager.items.get("Gardening Hoe").getItemStack());
                Chat.npc(player,"Hunter","Have this!");
                Chat.npc(player,"Hunter","Come back with 30 wheat");
                Chat.npc(player,"Hunter","and I'll give you the special boots.");
                QuestData.setCompletion(player, 3, 2,"Collect 30 wheat");
                return;
            }
        }
    }
}
