package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.managers.*;
import me.nils.everhunt.utils.Chat;
import me.nils.everhunt.utils.Condition;
import me.nils.everhunt.utils.Stats;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MarketData {
    public static final HashMap<String, MarketData> data = new HashMap<>();
    private String user;
    private String item;
    private int amount;
    private int price;
    private boolean sold;
    private boolean collected;

    public MarketData(String user, String item, int amount, int price, boolean sold, boolean collected) {
        this.user = user;
        this.item = item;
        this.amount = amount;
        this.price = price;
        this.sold = sold;
        this.collected = collected;

        data.put(user+" "+item,this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblmarket WHERE user = '" + user + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblmarket (user, item, amount, price, sold, collected) VALUES ('" + user + "','" + item + "','" +
                        amount + "','" + price + "','" + sold + "','" + collected + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void register() {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblmarket").executeQuery();

            while (resultSet.next()) {
                String user = resultSet.getString("user");
                String item = resultSet.getString("item");
                int amount = resultSet.getInt("amount");
                int price = resultSet.getInt("price");
                boolean sold = resultSet.getBoolean("sold");
                boolean collected = resultSet.getBoolean("collected");

                new MarketData(user,item,amount,price,sold,collected);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ItemStack> getAllProducts() {
        ArrayList<ItemStack> list = new ArrayList<>();
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblmarket").executeQuery();

            while (resultSet.next()) {
                String user = resultSet.getString("user");
                String item = resultSet.getString("item");
                int amount = resultSet.getInt("amount");
                int price = resultSet.getInt("price");
                boolean sold = resultSet.getBoolean("sold");
                boolean collected = resultSet.getBoolean("collected");

                if (sold || collected) {
                    if (Condition.isCustom(item)) {
                        ItemStack itemStack = switch (Condition.getType(item)) {
                            case ITEM -> ItemManager.items.get(item).getItemStack();
                            case DISH -> DishManager.items.get(item).getItemStack();
                            case SOUL -> SoulManager.souls.get(item).getItemStack();
                            case TOOL -> ToolManager.items.get(item).getItemStack();
                            case ARMOR -> ArmorManager.items.get(item).getItemStack();
                            case HELMET -> HelmetManager.items.get(item).getItemStack();
                            case WEAPON -> WeaponManager.items.get(item).getItemStack();
                            default -> null;
                        };

                        itemStack.setAmount(amount);
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        List<String> lore = itemMeta.getLore();
                        lore.add(Chat.color("&fPrice: &6" + price));
                        lore.add(Chat.color("&fSeller: &9" + user));
                        itemMeta.setLore(lore);

                        itemMeta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, user);
                        itemMeta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.INTEGER, price);

                        itemStack.setItemMeta(itemMeta);

                        list.add(itemStack);
                    }
                }
            }

            return list;
        } catch (SQLException e) {
            return list;
        }
    }

    public static ArrayList<ItemStack> getYourProducts(Player player) {
        ArrayList<ItemStack> list = new ArrayList<>();
        String user = player.getName();
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblmarket WHERE user = '" + user + "'").executeQuery();

            while (resultSet.next()) {
                String item = resultSet.getString("item");
                int amount = resultSet.getInt("amount");
                int price = resultSet.getInt("price");
                boolean sold = resultSet.getBoolean("sold");
                boolean collected = resultSet.getBoolean("collected");

                if (!collected) {
                    if (Condition.isCustom(item)) {
                        ItemStack itemStack = switch (Condition.getType(item)) {
                            case ITEM -> ItemManager.items.get(item).getItemStack();
                            case DISH -> DishManager.items.get(item).getItemStack();
                            case SOUL -> SoulManager.souls.get(item).getItemStack();
                            case TOOL -> ToolManager.items.get(item).getItemStack();
                            case ARMOR -> ArmorManager.items.get(item).getItemStack();
                            case HELMET -> HelmetManager.items.get(item).getItemStack();
                            case WEAPON -> WeaponManager.items.get(item).getItemStack();
                            default -> null;
                        };

                        itemStack.setAmount(amount);
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        List<String> lore = itemMeta.getLore();
                        lore.add(Chat.color("&fPrice: &6" + price));
                        lore.add(Chat.color("&fSeller: &9" + user));
                        if (sold) lore.add(Chat.color("&aSOLD"));
                        itemMeta.setLore(lore);

                        itemMeta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, user);
                        itemMeta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.INTEGER, price);

                        itemStack.setItemMeta(itemMeta);

                        list.add(itemStack);
                    }
                }
            }

            return list;
        } catch (SQLException e) {
            return list;
        }
    }

    public static ArrayList<ItemStack> getSearchResults(String prompt) {
        ArrayList<ItemStack> list = new ArrayList<>();
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblmarket WHERE item LIKE '%" + prompt + "%'").executeQuery();

            while (resultSet.next()) {
                String user = resultSet.getString("user");
                String item = resultSet.getString("item");
                int amount = resultSet.getInt("amount");
                int price = resultSet.getInt("price");
                boolean sold = resultSet.getBoolean("sold");
                boolean collected = resultSet.getBoolean("collected");

                if (!collected) {
                    if (Condition.isCustom(item)) {
                        ItemStack itemStack = switch (Condition.getType(item)) {
                            case ITEM -> ItemManager.items.get(item).getItemStack();
                            case DISH -> DishManager.items.get(item).getItemStack();
                            case SOUL -> SoulManager.souls.get(item).getItemStack();
                            case TOOL -> ToolManager.items.get(item).getItemStack();
                            case ARMOR -> ArmorManager.items.get(item).getItemStack();
                            case HELMET -> HelmetManager.items.get(item).getItemStack();
                            case WEAPON -> WeaponManager.items.get(item).getItemStack();
                            default -> null;
                        };

                        itemStack.setAmount(amount);
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        List<String> lore = itemMeta.getLore();
                        lore.add(Chat.color("&fPrice: &6" + price));
                        lore.add(Chat.color("&fSeller: &9" + user));
                        if (sold) lore.add(Chat.color("&aSOLD"));
                        itemMeta.setLore(lore);

                        itemMeta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, user);
                        itemMeta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.INTEGER, price);

                        itemStack.setItemMeta(itemMeta);

                        list.add(itemStack);
                    }
                }
            }

            return list;
        } catch (SQLException e) {
            return list;
        }
    }

    public boolean isSold() {
        return sold;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
        try {
            Everhunt.getDatabase().run("UPDATE tblmarket SET sold ='" + sold + "' WHERE user = '" + user + "' AND item = '" + item + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
        try {
            Everhunt.getDatabase().run("UPDATE tblmarket SET collected ='" + collected + "' WHERE user = '" + user + "' AND item = '" + item + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemStack getOwnProductsHead(Player player) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setPlayerProfile(player.getPlayerProfile());
        meta.displayName(Component.text("Your products"));
        meta.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING, player.getName());
        meta.setUnbreakable(true);

        List<String> lore = new ArrayList<>();

        lore.add(Chat.color("&fClick to open own products"));

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
