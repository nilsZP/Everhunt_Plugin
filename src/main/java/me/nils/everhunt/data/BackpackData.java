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
            Everhunt.getDatabase().run("REPLACE INTO tblbackpack (uuid, name, amount, slot) VALUES ('" + uuid + "','" + name + "','" + amount + "','" +
                        slot + "')").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<ItemStack> getContents(String uuid) {
        ArrayList<ItemStack> itemList = new ArrayList<>();

        try {
            for (int i = 0; i < 54; i++) {
                ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblbackpack WHERE uuid = '" + uuid + "' AND slot = '" + i + "'").executeQuery();
                ItemStack item;

                if (resultSet.next()) {
                    String name = resultSet.getString("name");  // what if name doesn't exist???

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
