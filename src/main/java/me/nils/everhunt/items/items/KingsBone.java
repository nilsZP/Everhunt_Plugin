package me.nils.everhunt.items.items;

import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Material;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class KingsBone extends ItemManager {
    public KingsBone() throws MalformedURLException {
        super(Material.BONE,"Kings Bone", Tier.NATURAL,80, "");
    }
}
