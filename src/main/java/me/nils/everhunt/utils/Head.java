package me.nils.everhunt.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class Head {
    public static PlayerProfile createTexture(URL url) {
        UUID uuid = UUID.randomUUID();
        PlayerProfile profile = Bukkit.createProfile(uuid);
        PlayerTextures playerTexture = profile.getTextures();
        playerTexture.setSkin(url);
        profile.setTextures(playerTexture);
        return profile;
    }

    public static PlayerProfile createTexture(String url) {
        URL textureUrl = null;
        try {
            textureUrl = new URL("https://textures.minecraft.net/texture/" + url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return createTexture(textureUrl);
    }
}
