package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.managers.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

public class BackpackData {

    public BackpackData(String uuid, String name, int amount, int slot) {
        try {
            Everhunt.getDatabase().run("UPDATE tblbackpack SET name = '" + name + "', amount = '" + amount + "' WHERE uuid = '" +
                    uuid + "' AND slot = '" + slot + "'").executeUpdate();
        } catch (SQLException e) {
            try {
                Everhunt.getDatabase().run("INSERT INTO tblbackpack (uuid, name, amount, slot) VALUES ('" + uuid + "','" + name + "','" + amount + "','" +
                        slot + "')").executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static ArrayList<ItemStack> getContents(String uuid) {
        ArrayList<ItemStack> itemList = new ArrayList<>();

        try {
            for (int i = 0; i < 54; i++) {
                ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblbackpack WHERE uuid = '" + uuid + "' AND slot = '" + i + "'").executeQuery();
                ItemStack item;

                if (resultSet.next()) {
                    String name = resultSet.getString("name");

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
                        item = new ItemStack(Material.AIR);
                    }

                    item.setAmount(resultSet.getInt("amount"));
                } else {
                    item = new ItemStack(Material.AIR);
                }

                itemList.add(item);
            }

            return itemList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
