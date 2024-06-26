package me.nils.everhunt.utils;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.ItemType;
import me.nils.everhunt.managers.*;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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

            return amount <= itemAmount;
        }

        return false;
    }

    public static boolean has(Player player, String name, ItemType type, int amount) {
        Inventory inventory = player.getInventory();
        int count = 0;

        for (ItemStack itemStack : inventory) {
            if (itemStack != null) {
                if (itemStack.getItemMeta() != null) {
                    String itemName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
                    if (isCustom(type, itemName)) {
                        if (itemName.equals(name)) {
                            count += itemStack.getAmount();
                        }
                    }
                }
            }
        }

        return count >= amount;
    }

    public static boolean isMineable(Block block) {
        return switch (block.getType()) {
            case STONE,COAL_ORE,IRON_ORE,RAW_GOLD_BLOCK,GOLD_ORE,DEEPSLATE_GOLD_ORE,NETHER_GOLD_ORE -> true;
            default -> false;
        };
    }

    public static boolean isFarmeable(Block block) {
        return switch (block.getType()) {
            case WHEAT,CARROTS,POTATOES,BEETROOTS -> true;
            default -> false;
        };
    }

    public static boolean canMine(String item, Block block) {
        if (isMineable(block)) {
            if (isCustom(ItemType.TOOL,item)) {
                switch (block.getType()) {
                    case STONE, COAL_ORE, IRON_ORE, GOLD_ORE -> {
                        return true;
                    }
                    case RAW_GOLD_BLOCK,DEEPSLATE_GOLD_ORE,NETHER_GOLD_ORE -> {
                        return item.contains("G");
                    }
                }
            }
        }

        return false;
    }

    public static boolean isCustom(ItemType type, String item) {
        switch (type) {
            case ITEM -> {
                return ItemManager.items.get(item) != null;
            }
            case TOOL -> {
                return ToolManager.items.get(item) != null;
            }
            case ARMOR -> {
                return ArmorManager.items.get(item) != null;
            }
            case HELMET -> {
                return HelmetManager.items.get(item) != null;
            }
            case WEAPON -> {
                return WeaponManager.items.get(item) != null;
            }
            case SOUL -> {
                return SoulManager.souls.get(item) != null;
            }
            case DISH -> {
                return DishManager.items.get(item) != null;
            }
            default -> {
                return false;
            }
        }
    }

    public static boolean isCustom(String item) {
        return (isCustom(ItemType.ITEM,item) || isCustom(ItemType.TOOL,item) || isCustom(ItemType.HELMET,item) || isCustom(ItemType.WEAPON,item) || isCustom(ItemType.ARMOR,item)
        || isCustom(ItemType.SOUL,item) || isCustom(ItemType.DISH,item));
    }

    public static ItemType getType(String item) {
        if (ItemManager.items.get(item) != null) return ItemType.ITEM;
        if (WeaponManager.items.get(item) != null) return ItemType.WEAPON;
        if (SoulManager.souls.get(item) != null) return ItemType.SOUL;
        if (DishManager.items.get(item) != null) return ItemType.DISH;
        if (ArmorManager.items.get(item) != null) return ItemType.ARMOR;
        if (HelmetManager.items.get(item) != null) return ItemType.HELMET;
        if (ToolManager.items.get(item) != null) return ItemType.TOOL;

        return null;
    }

    public static boolean itemNameContains(Player player, String text) {
        ItemStack item = player.getInventory().getItemInMainHand();
        String name = ChatColor.stripColor(item.displayName().toString());

        if (name != null) {
            return name.contains(text);
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
