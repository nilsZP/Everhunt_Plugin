package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.items.Items;
import me.nils.everhunt.managers.*;
import me.nils.everhunt.utils.Condition;
import org.bukkit.ChatColor;
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
            if (Everhunt.getDatabase().run("UPDATE tblbackpack SET name = '" + name + "', amount = '" + amount + "' WHERE uuid = '" +
                    uuid + "' AND slot = '" + slot + "'").executeUpdate() == 0) {
                try {
                    Everhunt.getDatabase().run("INSERT INTO tblbackpack (uuid, name, amount, slot) VALUES ('" + uuid + "','" + name + "','" + amount + "','" +
                            slot + "')").executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeData(String uuid, int slot) {
        try {
            Everhunt.getDatabase().run("DELETE FROM tblbackpack WHERE uuid = '" +
                    uuid + "' AND slot = '" + slot + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<ItemStack> getContents(String uuid) {
        ArrayList<ItemStack> itemList = new ArrayList<>();

        try {
            for (int i = 0; i < 54; i++) {
                ResultSet resultSet = Everhunt.getDatabase().run("SELECT * FROM tblbackpack WHERE uuid = '" + uuid + "' AND slot = '" + i + "'").executeQuery();
                ItemStack itemStack;

                if (resultSet.next()) {
                    String item = resultSet.getString("name");

                    ItemStack base = Items.getBase(item);

                    itemStack = new ItemStack(base);

                    itemStack.setAmount(resultSet.getInt("amount"));
                } else {
                    itemStack = new ItemStack(Material.AIR);
                }

                itemList.add(itemStack);
            }

            return itemList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
