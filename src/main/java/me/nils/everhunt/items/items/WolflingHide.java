package me.nils.everhunt.items.items;

import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Material;

import java.net.MalformedURLException;
import java.net.URL;

public class WolflingHide extends ItemManager {
    public WolflingHide() throws MalformedURLException {
        super(Material.RABBIT_HIDE,"Wolfling Hide", Tier.NATURAL,5, new URL("https://theuselessweb.com/"));
    }
}
