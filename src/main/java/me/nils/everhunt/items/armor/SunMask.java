package me.nils.everhunt.items.armor;

import me.nils.everhunt.constants.Ability;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.managers.HelmetManager;

import java.net.MalformedURLException;
import java.net.URL;

public class SunMask extends HelmetManager {
    public SunMask() throws MalformedURLException {
        super("SunMask", Ability.NONE, Tier.MAGICAL,3,4,1,2,new URL("https://textures.minecraft.net/texture/7dcac37609e68a110dabba70f438609bace1a674b369d577eac623850d04fba6"));
    }
}
