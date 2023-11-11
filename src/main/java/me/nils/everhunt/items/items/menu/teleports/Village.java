package me.nils.everhunt.items.items.menu.teleports;

import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.ItemManager;
import org.bukkit.Material;

import java.net.MalformedURLException;
import java.net.URL;

public class Village extends ItemManager {
    public Village() {
        super(Material.PLAYER_HEAD,"Village", Tier.MENU,0,"https://textures.minecraft.net/texture/45f39ceb7ec2361278d1b8f9fc846ed1486af722d21a2b3fe14a28e3ccee0709");
    }
}
