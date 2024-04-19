package me.nils.everhunt.entities;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.MobType;
import me.nils.everhunt.constants.Tier;
import me.nils.everhunt.data.EntityData;
import me.nils.everhunt.items.Items;
import me.nils.everhunt.utils.Chat;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Stray;
import org.bukkit.persistence.PersistentDataType;

public class LostSoul extends EntityData {
    public LostSoul(Location loc) {
        super("Lost Soul", Tier.SOUL, MobType.ENEMY);
        Phantom scare = (Phantom) loc.getWorld().spawnEntity(loc, EntityType.PHANTOM);

        scare.setCustomName(Chat.color(String.format("%s &c%d&f/&c%d%s", super.getDisplayName(), 25, 25,"♥")));
        scare.setCustomNameVisible(true);
        scare.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING,super.getDisplayName());

        scare.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(25);
        scare.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6);
        scare.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(8);

        super.setEntity(scare);
    }
}
