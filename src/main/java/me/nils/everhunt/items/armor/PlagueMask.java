package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.HelmetManager;

import java.net.MalformedURLException;
import java.net.URL;

public class PlagueMask extends HelmetManager {
    public PlagueMask() throws MalformedURLException {
        super("PlagueMask", Ability.INFECTATION, Tier.CURSED,2,4,2,2,new URL("https://textures.minecraft.net/texture/bed099416323172c9d1ff832e1ae17e4ba55fe00fbd0eeb0326f9cfbc6d5949"));
    }
}
