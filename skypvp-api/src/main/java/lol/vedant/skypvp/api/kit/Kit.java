package lol.vedant.skypvp.api.kit;


import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kit {

    private final String id;

    private Map<Integer, ItemStack> inventory = new HashMap<>();
    private Map<String, Object> options = new HashMap<>();
    private List<PotionEffect> potionEffects = new ArrayList<>();

    private ItemStack kitHelmet;
    private ItemStack kitChestplate;
    private ItemStack kitLeggings;
    private ItemStack kitBoots;

    private String displayName;
    private ItemStack displayIcon;
    private int price;

    private ItemStack offHand;

    public Kit(String id) {
        this.id = id;
    }

    public Map<Integer, ItemStack> getInventory() {
        return inventory;
    }

    public void setInventory(Map<Integer, ItemStack> inventory) {
        this.inventory = inventory;
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

    public ItemStack getKitHelmet() {
        return kitHelmet;
    }

    public void setKitHelmet(ItemStack kitHelmet) {
        this.kitHelmet = kitHelmet;
    }

    public ItemStack getKitChestplate() {
        return kitChestplate;
    }

    public void setKitChestplate(ItemStack kitChestplate) {
        this.kitChestplate = kitChestplate;
    }

    public ItemStack getKitLeggings() {
        return kitLeggings;
    }

    public void setKitLeggings(ItemStack kitLeggings) {
        this.kitLeggings = kitLeggings;
    }

    public ItemStack getKitBoots() {
        return kitBoots;
    }

    public void setKitBoots(ItemStack kitBoots) {
        this.kitBoots = kitBoots;
    }

    public String getId() {
        return id;
    }

    public ItemStack getOffHand() {
        return offHand;
    }

    public void setOffHand(ItemStack offHand) {
        this.offHand = offHand;
    }

    public ItemStack getDisplayIcon() {
        return displayIcon;
    }

    public void setDisplayIcon(ItemStack displayIcon) {
        this.displayIcon = displayIcon;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
