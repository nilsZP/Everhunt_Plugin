package me.nils.vdvcraftrevamp.entities.bosses.kings;

import me.nils.vdvcraftrevamp.constants.Ability;
import me.nils.vdvcraftrevamp.constants.MobType;
import me.nils.vdvcraftrevamp.data.EntityData;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.List;

public class WolfKing extends EntityData {
    public WolfKing(Location loc) {
        super("Wolf King", Ability.NONE, MobType.BOSS);
        Wolf wolf = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);

        wolf.setAngry(true);
        List<Entity> entityList = wolf.getNearbyEntities(5,5,5);
        for (Entity entity : entityList) {
            if (entity instanceof Player player) {
                wolf.setTarget(player);
            }
        }

        wolf.setCustomName("Wolf King");
        wolf.setCustomNameVisible(true);


        wolf.setMaxHealth(25);
        wolf.setHealth(25);
    }
}
