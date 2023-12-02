package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.managers.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BackpackData {
    private final String uuid;
    private final String name;
    private final int amount;
    private final int slot;

    public BackpackData(String uuid, String name, int amount, int slot) {
        this.uuid = uuid;
        this.name = name;
        this.amount = amount;
        this.slot = slot;

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblbackpack WHERE uuid = '" + uuid + "' AND slot = '" + slot + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tblbackpack (uuid, type, name, amount, slot) VALUES ('" + uuid + "','" + name + "','" + amount + "','" +
                        slot + "')").executeUpdate();
            } else {
                Everhunt.getDatabase().run("UPDATE tblbackpack SET name = '" + name + "', amount = '" + amount + "' WHERE uuid = '" +
                        uuid + "' AND slot = '" + slot + "'").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<ItemStack> getContents(String uuid) {
        ArrayList<ItemStack> itemList = new ArrayList<>();

        try {
            for (int i = 1; i <= 54; i++) {
                ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblbackpack WHERE uuid = '" + uuid + "' AND slot = '" + i + "'").executeQuery();

                resultSet.next();
                String name = resultSet.getString("name");
                ItemStack item;

                if (WeaponManager.items.get(name) != null) {
                    item = WeaponManager.items.get(name).getItemStack();
                } else if (ArmorManager.items.get(name) != null) {
                    item = ArmorManager.items.get(name).getItemStack();
                } else if (HelmetManager.items.get(name) != null) {
                    item = HelmetManager.items.get(name).getItemStack();
                } else if (ItemManager.items.get(name) != null) {
                    item = ItemManager.items.get(name).getItemStack();
                } else if (ToolManager.items.get(name) != null) {
                    item = ToolManager.items.get(name).getItemStack();
                } else {
                    item = null;
                }

                item.setAmount(resultSet.getInt("amount"));

                itemList.add(item);
            }

            return itemList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    public static boolean doesExist(String uuid) {
        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tblbackpack WHERE uuid = '" + uuid + "'").executeQuery();
            check.next();
            if (check.getInt(1) >= 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
