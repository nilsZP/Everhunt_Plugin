package me.nils.everhunt.menu.standard;

import me.nils.everhunt.menu.Menu;
import me.nils.everhunt.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MoldSelectionMenu extends Menu {
    public MoldSelectionMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Mold Selection";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getCurrentItem().getType() != Material.GRAY_STAINED_GLASS_PANE) {
            playerMenuUtility.setMold(e.getCurrentItem().getType());
            new WeaponCreationMenu(playerMenuUtility, "").open();
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack[] list = {new ItemStack(Material.WOODEN_SWORD),new ItemStack(Material.STONE_SWORD),new ItemStack(Material.IRON_SWORD),new ItemStack(Material.GOLDEN_SWORD),new ItemStack(Material.DIAMOND_SWORD),new ItemStack(Material.NETHERITE_SWORD),
                new ItemStack(Material.WOODEN_PICKAXE),new ItemStack(Material.STONE_PICKAXE),new ItemStack(Material.IRON_PICKAXE),new ItemStack(Material.GOLDEN_PICKAXE),new ItemStack(Material.DIAMOND_PICKAXE),new ItemStack(Material.NETHERITE_PICKAXE),
                new ItemStack(Material.WOODEN_SHOVEL),new ItemStack(Material.STONE_SHOVEL),new ItemStack(Material.IRON_SHOVEL),new ItemStack(Material.GOLDEN_SHOVEL),new ItemStack(Material.DIAMOND_SHOVEL),new ItemStack(Material.NETHERITE_SHOVEL),
                new ItemStack(Material.WOODEN_HOE),new ItemStack(Material.STONE_HOE),new ItemStack(Material.IRON_HOE),new ItemStack(Material.GOLDEN_HOE),new ItemStack(Material.DIAMOND_HOE),new ItemStack(Material.NETHERITE_HOE),
                new ItemStack(Material.WOODEN_AXE),new ItemStack(Material.STONE_AXE),new ItemStack(Material.IRON_AXE),new ItemStack(Material.GOLDEN_AXE),new ItemStack(Material.DIAMOND_AXE),new ItemStack(Material.NETHERITE_AXE)};
        inventory.setContents(list);
        setFillerGlass();
    }
}
