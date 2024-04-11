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
import org.bukkit.entity.Stray;
import org.bukkit.persistence.PersistentDataType;

public class ForgottenSoul extends EntityData {
    public ForgottenSoul(Location loc) {
        super("Forgotten Soul", Tier.SOUL, MobType.ENEMY);
        Stray scare = (Stray) loc.getWorld().spawnEntity(loc, EntityType.STRAY);

        scare.setCustomName(Chat.color(String.format("%s &c%d&f/&c%d%s", super.getDisplayName(), 30, 30,"♥")));
        scare.setCustomNameVisible(true);
        scare.getPersistentDataContainer().set(Everhunt.getKey(), PersistentDataType.STRING,super.getDisplayName());

        scare.getEquipment().setItemInMainHand(Items.getBase("Lucia II"));

        scare.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
        scare.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(8);
        scare.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);

        super.setEntity(scare);
    }
}
