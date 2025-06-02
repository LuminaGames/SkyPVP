package lol.vedant.skypvp.api.kit;

import com.cryptomorin.xseries.XItemStack;
import com.cryptomorin.xseries.XMaterial;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class KitSerializer {

    private final Plugin plugin;
    private final File kitFolder;

    public KitSerializer(Plugin plugin) {
        this.plugin = plugin;
        this.kitFolder = new File(plugin.getDataFolder(), "kits");
    }

    /*
    Makes it, so you can save a player's inventory and active potion effects in a kit
    */
    public void saveKit(String id, Player player) {
        PlayerInventory inventory = player.getInventory();

        File kitFile = new File(kitFolder, id + ".yml");

        if (!kitFolder.exists()) {
            kitFolder.mkdirs();
        } else {
            if (!kitFile.exists()) {
                try {
                    kitFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(kitFile);

        // Save Inventory
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (item != null) {
                Map<String, Object> serializedItem = XItemStack.serialize(item);
                config.set("kit." + id + ".inventory." + slot, serializedItem);
            }
        }

        // Save Armor
        ItemStack[] armorContents = inventory.getArmorContents();
        for (int slot = 0; slot < armorContents.length; slot++) {
            ItemStack item = armorContents[slot];
            if (item == null || item.getType() == Material.AIR) {
                continue; // Skip null or air items
            }

            Map<String, Object> serializedItem = XItemStack.serialize(item);
            config.set("kit." + id + ".armor." + slot, serializedItem);
        }

        ItemStack displayItem = XMaterial.IRON_SWORD.parseItem();
        ItemMeta meta = displayItem.getItemMeta();
        meta.setDisplayName(Utils.cc("&7&l" + id + " Kit"));
        meta.setLore(Utils.cc(Arrays.asList("&7Left-Click to buy", "Right-Click to preview kit")));

        config.set("kit." + id + ".displayItem", XItemStack.serialize(displayItem));
        config.set("kit." + id + ".displayName", id);

        // Save Potion Effects
        int effectIndex = 0;
        for (PotionEffect effect : player.getActivePotionEffects()) {
            Map<String, Object> serializedPotion = effect.serialize();
            config.set("kit." + id + ".effects." + effectIndex, serializedPotion);
            effectIndex++;
        }

        try {
            config.save(kitFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Kit loadKit(String id) {
        File kitFile = new File(kitFolder, id + ".yml");

        if (!kitFile.exists()) {
            return null;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(kitFile);
        Kit kit = new Kit(id);
        int price = config.getInt("kit." + id + ".price");
        kit.setPrice(price);

        // Load Inventory
        Map<Integer, ItemStack> inventory = new HashMap<>();
        for (String key : config.getConfigurationSection("kit." + id + ".inventory").getKeys(false)) {
            int slot = Integer.parseInt(key);
            ItemStack item = XItemStack.deserialize(config.getConfigurationSection("kit." + id + ".inventory." + slot).getValues(true));
            inventory.put(slot, item);
        }
        kit.setInventory(inventory);

        // Load Armor
        ItemStack[] armorContents = new ItemStack[4];
        if(config.getConfigurationSection("kit." + id + ".armor") != null) {

            for (String key : config.getConfigurationSection("kit." + id + ".armor").getKeys(false)) {
                int slot = Integer.parseInt(key);

                ItemStack item = XItemStack.deserialize(config.getConfigurationSection("kit." + id + ".armor." + slot).getValues(true));
                armorContents[slot] = item;
            }
            if (armorContents.length > 0) {
                kit.setKitHelmet(armorContents[3]);
                kit.setKitChestplate(armorContents[2]);
                kit.setKitLeggings(armorContents[1]);
                kit.setKitBoots(armorContents[0]);
            }
        }
        // Load Potion Effects
        //List<PotionEffect> potionEffects = new ArrayList<>();
        //for (String key : config.getConfigurationSection("kit." + id + ".effects").getKeys(false)) {
        //    Map<String, Object> effectData = config.getConfigurationSection("kit." + id + ".effects." + key).getValues(true);
        //    PotionEffectType type = PotionEffectType.getByName((String) effectData.get("type"));
        //    int duration = (int) effectData.get("duration");
        //    int amplifier = (int) effectData.get("amplifier");
        //    boolean ambient = (boolean) effectData.get("ambient");
        //    boolean particles = (boolean) effectData.get("particles");
        //    boolean icon = (boolean) effectData.get("icon");
//
        //    PotionEffect effect = new PotionEffect(type, duration, amplifier, ambient, particles, icon);
        //    potionEffects.add(effect);
        //}
        //kit.setPotionEffects(potionEffects);

        return kit;
    }


}
