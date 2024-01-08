package me.nils.everhunt.items;

import com.google.gson.Gson;
import me.nils.everhunt.Everhunt;
import me.nils.everhunt.managers.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Backpack {
    private final int size = 54;
    private ItemStack[] contents;

    public Backpack() {
        this.contents = new ItemStack[size];
    }

    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    public static void saveBackpackData(String uuid, ItemStack[] contents) {
        String serializedContents = serializeContents(contents);

        try (PreparedStatement statement = Everhunt.getDatabase().run(
                "INSERT OR REPLACE INTO backpacks (player_uuid, backpack_size, backpack_contents) VALUES (?, ?, ?)")) {
            statement.setString(1, uuid);
            statement.setInt(2, 54);
            statement.setString(3, serializedContents);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ItemStack[] loadBackpackData(String uuid) {
        try (PreparedStatement statement = Everhunt.getDatabase().run(
                "SELECT backpack_size, backpack_contents FROM backpacks WHERE player_uuid = ?")) {
            statement.setString(1, uuid);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int size = resultSet.getInt("backpack_size");
                String serializedContents = resultSet.getString("backpack_contents");
                return deserializeContents(serializedContents);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String serializeContents(ItemStack[] contents) {
        Gson gson = new Gson();
        return gson.toJson(contents);
    }

    private static ItemStack[] deserializeContents(String serializedData) {
        Gson gson = new Gson();
        return gson.fromJson(serializedData, ItemStack[].class);
    }

}
