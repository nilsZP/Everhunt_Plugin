package me.nils.everhunt.items.items;

import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.items.tools.WheatHoe;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Material;

import java.net.MalformedURLException;
import java.net.URL;

public class Wheat extends ItemManager {
    public Wheat() throws MalformedURLException {
        super(Material.WHEAT,"Wheat", Tier.NATURAL,1,new URL("https://theuselessweb.com/"));
    }
}
