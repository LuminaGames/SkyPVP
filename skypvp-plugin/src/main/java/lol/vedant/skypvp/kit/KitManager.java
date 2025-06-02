package lol.vedant.skypvp.kit;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.Kit;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class KitManager {

    private SkyPVP plugin;

    private Map<String, Kit> loadedKits = new HashMap<>();

    public KitManager(SkyPVP plugin) {
        this.plugin = plugin;
    }

    public void load() {
        loadedKits.clear();
        File folder = new File(plugin.getDataFolder(), "kits");
        if(folder.listFiles() == null) {
            return;
        }

        for (File kit : folder.listFiles()) {
            if(kit.getName().endsWith(".yml")) {
                String id = kit.getName().replace(".yml", "");
                Kit k = plugin.getKitSerializer().loadKit(id);
                loadedKits.put(id, k);
                plugin.getLogger().info("Kit " + id + " was loaded successfully");
            }
        }
    }

    public void giveKit(Player player, Kit kit) {
        PlayerInventory inventory = player.getInventory();

        // Clear current inventory and potion effects
        inventory.clear();
        player.getActivePotionEffects().clear();

        // Set inventory items
        for (Map.Entry<Integer, ItemStack> entry : kit.getInventory().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        // Set armor
        inventory.setHelmet(kit.getKitHelmet());
        inventory.setChestplate(kit.getKitChestplate());
        inventory.setLeggings(kit.getKitLeggings());
        inventory.setBoots(kit.getKitBoots());

        // Set off-hand item for 1.9+ Game versions
        if(Utils.getServerVersion() >= 19) {
            inventory.setItemInOffHand(kit.getOffHand());
        }

        // Apply potion effects
        for (PotionEffect effect : kit.getPotionEffects()) {
            player.addPotionEffect(effect);
        }
    }

    public Kit getKitById(String id) {
        return loadedKits.get(id);
    }

    public YamlConfiguration getKitFile(String id) {
        File folder = new File(plugin.getDataFolder(), "kits");
        File kit = new File(folder, id + ".yml");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(kit);
        return config;
    }

    public void saveKitFile(String id, YamlConfiguration config) {
        File folder = new File(plugin.getDataFolder(), "kits");
        File kit = new File(folder, id + ".yml");

        try {
            config.save(kit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public Map<String, Kit> getLoadedKits() {
        return loadedKits;
    }


}
