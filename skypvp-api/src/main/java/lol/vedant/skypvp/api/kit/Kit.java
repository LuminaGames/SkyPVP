package lol.vedant.skypvp.api.kit;

import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kit {

    private final String name;
    private String permission;

    //Armor Contents
    private ItemStack helmetItem;
    private ItemStack chestplateItem;
    private ItemStack leggingsItem;
    private ItemStack bootsItem;

    private ItemStack offHandItem;

    private Map<Integer, ItemStack> inventory;
    private Map<String, Object> options;
    private List<PotionEffect> potionEffects;

    public Kit(String name) {
        this.name = name;
        this.inventory = new HashMap<>();
        this.options = new HashMap<>();
        this.potionEffects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public ItemStack getHelmetItem() {
        return helmetItem;
    }

    public void setHelmetItem(ItemStack helmetItem) {
        this.helmetItem = helmetItem;
    }

    public ItemStack getChestplateItem() {
        return chestplateItem;
    }

    public void setChestplateItem(ItemStack chestplateItem) {
        this.chestplateItem = chestplateItem;
    }

    public ItemStack getLeggingsItem() {
        return leggingsItem;
    }

    public void setLeggingsItem(ItemStack leggingsItem) {
        this.leggingsItem = leggingsItem;
    }

    public ItemStack getBootsItem() {
        return bootsItem;
    }

    public void setBootsItem(ItemStack bootsItem) {
        this.bootsItem = bootsItem;
    }

    public ItemStack getOffHandItem() {
        return offHandItem;
    }

    public void setOffHandItem(ItemStack offHandItem) {
        this.offHandItem = offHandItem;
    }

    public Map<Integer, ItemStack> getInventory() {
        return inventory;
    }

    public void setInventoryItem(int slot, ItemStack item) {
        inventory.put(slot, item);
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public List<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public void setPotionEffects(List<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    public void apply(Player player) {

        List<ItemStack> overflowItems = new ArrayList<>();
        boolean addOverflowItems = (Boolean) options.get("AddOverflowItemsOnKit");

        if (helmetItem != null) {
            if (player.getInventory().getHelmet() == null) {
                player.getInventory().setHelmet(helmetItem);
            } else {
                overflowItems.add(helmetItem);
            }
        }

        if (chestplateItem != null) {
            if (player.getInventory().getChestplate() == null) {
                player.getInventory().setChestplate(chestplateItem);
            } else {
                overflowItems.add(chestplateItem);
            }
        }

        if (leggingsItem != null) {
            if (player.getInventory().getLeggings() == null) {
                player.getInventory().setLeggings(leggingsItem);
            } else {
                overflowItems.add(leggingsItem);
            }
        }

        if (bootsItem != null) {
            if (player.getInventory().getBoots() == null) {
                player.getInventory().setBoots(bootsItem);
            } else {
                overflowItems.add(bootsItem);
            }
        }

        for (int i = 0; i < 36; i++) {
            if (addOverflowItems) {
                // if kit wants to put an item in slot i, but slot i in player inventory is already taken
                if (inventory.containsKey(i) && player.getInventory().getItem(i) != null) {
                    overflowItems.add(inventory.get(i)); // add kit item to overflow items
                    continue; // ignore this kit item, will be accounted for with giveOverflowItems
                }
            }

            if (inventory.get(i) != null) {
                player.getInventory().setItem(i, inventory.get(i));
            }
        }

        if (offHandItem != null && Utils.getServerVersion() >= 19) {
            player.getInventory().setItemInOffHand(offHandItem);
        }

        potionEffects.forEach(player::addPotionEffect);

    }
}
