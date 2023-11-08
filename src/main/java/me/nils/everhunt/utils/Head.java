package me.nils.everhunt.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.URL;
import java.util.UUID;

public class Head {
    public static PlayerProfile createTexture(URL url){
        UUID uuid = UUID.randomUUID();
        PlayerProfile profile = Bukkit.createProfile(uuid);
        PlayerTextures playerTexture = profile.getTextures();
        playerTexture.setSkin(url);
        profile.setTextures(playerTexture);
        return profile;
    }
}
