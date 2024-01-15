package me.nils.everhunt.utils;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.managers.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Condition {
    public static boolean isHolding(Player player, String name, ItemType type) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getItemMeta() == null) {
            return false;
        }

        ItemMeta meta = itemStack.getItemMeta();

        String holding = ChatColor.stripColor(meta.getDisplayName());

        switch (type) {
            case ITEM -> {
                if (ItemManager.items.get(holding) == null) {
                    return false;
                }

                return holding.equals(name);
            }
            case TOOL -> {
                if (ToolManager.items.get(holding) == null) {
                    return false;
                }

                return holding.equals(name);
            }
            case ARMOR -> {
                if (ArmorManager.items.get(holding) == null) {
                    return false;
                }

                return holding.equals(name);
            }
            case HELMET -> {
                if (HelmetManager.items.get(holding) == null) {
                    return false;
                }

                return holding.equals(name);
            }
            case WEAPON -> {
                if (WeaponManager.items.get(holding) == null) {
                    return false;
                }

                return holding.equals(name);
            }
            default -> {
                return false;
            }
        }
    }

    public static boolean isHolding(Player player, String name, ItemType type, int amount) {
        if (isHolding(player, name, type)) {
            int itemAmount = player.getInventory().getItemInMainHand().getAmount();

            return amount == itemAmount;
        }

        return false;
    }

    public static boolean itemNameContains(Player player, String text) {
        ItemStack item = player.getInventory().getItemInMainHand();
        String name = ChatColor.stripColor(item.displayName().toString());

        if (name != null) {
            if (name.contains(text)) {
                return true;
            }
        }

        return false;
    }

    public static boolean getFullSet(Ability ability, Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        Ability ability1;

        if (helmet == null) {
            return false;
        }
        HelmetManager helm = HelmetManager.items.get(ChatColor.stripColor(helmet.getItemMeta().getDisplayName()));
        if (helm == null) {
            ArmorManager helm2 = ArmorManager.items.get(ChatColor.stripColor(helmet.getItemMeta().getDisplayName()));
            if (helm2 == null) {
                return false;
            } else {
                ability1 = helm2.getAbility();
            }
        } else {
            ability1 = helm.getAbility();
        }

        ItemStack chestplate = player.getInventory().getChestplate();
        if (chestplate == null) {
            return false;
        }
        ArmorManager chest = ArmorManager.items.get(ChatColor.stripColor(chestplate.getItemMeta().getDisplayName()));
        if (chest == null) {
            return false;
        }
        Ability ability2 = chest.getAbility();

        ItemStack leggings = player.getInventory().getLeggings();
        if (leggings == null) {
            return false;
        }
        ArmorManager legs = ArmorManager.items.get(ChatColor.stripColor(leggings.getItemMeta().getDisplayName()));
        if (legs == null) {
            return false;
        }
        Ability ability3 = legs.getAbility();

        ItemStack boots = player.getInventory().getBoots();
        if (boots == null) {
            return false;
        }
        ArmorManager boot = ArmorManager.items.get(ChatColor.stripColor(boots.getItemMeta().getDisplayName()));
        if (boot == null) {
            return false;
        }
        Ability ability4 = boot.getAbility();

        if (ability1 == ability2 && ability3 == ability4 && ability1 == ability3) {
            if (ability1 == ability) {
                return true;
            }
        }

        return false;
    }
}
