package me.nils.vdvcraftrevamp.constants;

import org.bukkit.inventory.meta.trim.TrimMaterial;

public enum Trim {
    AMETHYST(TrimMaterial.AMETHYST),
    COPPER(TrimMaterial.COPPER),
    DIAMOND(TrimMaterial.DIAMOND),
    EMERALD(TrimMaterial.EMERALD),
    GOLD(TrimMaterial.GOLD),
    IRON(TrimMaterial.IRON),
    LAPIS(TrimMaterial.LAPIS),
    NETHERITE(TrimMaterial.NETHERITE),
    QUARTZ(TrimMaterial.QUARTZ),
    REDSTONE(TrimMaterial.REDSTONE),
    NONE(null);

    private final TrimMaterial material;

    Trim(TrimMaterial material) {
        this.material = material;
    }

    public TrimMaterial getMaterial() {
        return material;
    }
}
