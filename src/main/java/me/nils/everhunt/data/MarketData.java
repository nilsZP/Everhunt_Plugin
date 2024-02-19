package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.managers.*;
import me.nils.everhunt.utils.Condition;
import me.nils.everhunt.utils.Stats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MarketData {
    public static final HashMap<String, MarketData> data = new HashMap<>();
    private String uuid;
    private String item;
    private int amount;
    private int price;
    private boolean sold;
    private boolean collected;

    public MarketData(String uuid, String item, int amount, int price, boolean sold, boolean collected) {
        this.uuid = uuid;
        this.item = item;
        this.amount = amount;
        this.price = price;
        this.sold = sold;
        this.collected = collected;

        data.put(uuid+" "+item,this);

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblmarket WHERE uuid = '" + uuid + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblmarket (uuid, item, amount, price, sold, collected) VALUES ('" + uuid + "','" + item + "','" +
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
                String uuid = resultSet.getString("uuid");
                String item = resultSet.getString("item");
                int amount = resultSet.getInt("amount");
                int price = resultSet.getInt("price");
                boolean sold = resultSet.getBoolean("sold");
                boolean collected = resultSet.getBoolean("collected");

                new MarketData(uuid,item,amount,price,sold,collected);
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
                String uuid = resultSet.getString("uuid");
                String item = resultSet.getString("item");
                int amount = resultSet.getInt("amount");
                int price = resultSet.getInt("price");
                boolean sold = resultSet.getBoolean("sold");
                boolean collected = resultSet.getBoolean("collected");

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

                    // TODO continue
                }
                new MarketData(uuid,item,amount,price,sold,collected);
            }
        } catch (SQLException e) {
            return list;
        }
    }
}