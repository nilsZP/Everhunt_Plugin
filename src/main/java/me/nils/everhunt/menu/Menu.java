package me.nils.everhunt.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class Menu implements InventoryHolder {
    protected Inventory inventory;
    protected PlayerMenuUtility playerMenuUtility;
    protected ItemStack FILLER_GLASS = makeItem(Material.GRAY_STAINED_GLASS_PANE," ");
    protected ItemStack COOK_BUTTON = makeItem(Material.RED_STAINED_GLASS_PANE,ChatColor.RED+"Cook");
    protected ItemStack RESULT_GLASS = makeItem(Material.GREEN_STAINED_GLASS_PANE,ChatColor.GREEN+"Result");
    protected ItemStack BUY_BUTTON = makeItem(Material.GOLD_BLOCK,ChatColor.GOLD+"Buy");
    protected ItemStack SOLD_ICON = makeItem(Material.REDSTONE_BLOCK,ChatColor.RED+"Sold");
    protected ItemStack CLOSE_BUTTON = makeItem(Material.BARRIER,ChatColor.DARK_RED + "Close");
    protected ItemStack ADD_BUTTON = makeItem(Material.OAK_BUTTON, ChatColor.YELLOW + "Add product");
    protected ItemStack PRICE_SIGN = makeItem(Material.OAK_SIGN,"Set Price");
    protected ItemStack SEARCH_ICON = makeItem(Material.OAK_SIGN,"Search");

    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this,getSlots(),getMenuName());

        this.setMenuItems();

        playerMenuUtility.getOwner().openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void setFillerGlass(){
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null){
                inventory.setItem(i, FILLER_GLASS);
            }
        }
    }

    public static ItemStack makeItem(Material material, String displayName, String... lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);

        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);

        return item;
    }

}
