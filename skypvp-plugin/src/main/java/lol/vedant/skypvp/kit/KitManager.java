package lol.vedant.skypvp.kit;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.Kit;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.io.*;
import java.net.URL;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

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

        if (!folder.exists() || folder.listFiles() == null || folder.listFiles().length == 0) {
            extractDefaultKits();
        }

        File[] files = folder.listFiles();
        if (files == null) {
            plugin.getLogger().warning("No kits found to load.");
            return;
        }

        for (File kit : files) {
            if (kit.getName().endsWith(".yml")) {
                String id = kit.getName().replace(".yml", "");
                Kit k = plugin.getKitSerializer().loadKit(id);
                loadedKits.put(id, k);
                plugin.getLogger().info("Kit " + id + " was loaded successfully");
            }
        }

        plugin.getLogger().info("Successfully loaded " + loadedKits.size() + " kits");
    }


    private void extractDefaultKits() {
        String resourceFolder = "kits";
        File targetDir = new File(plugin.getDataFolder(), resourceFolder);

        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        try {
            CodeSource codeSource = getClass().getProtectionDomain().getCodeSource();
            if (codeSource != null) {
                URL jarUrl = codeSource.getLocation();
                try (JarInputStream jarInputStream = new JarInputStream(jarUrl.openStream())) {
                    JarEntry entry;

                    while ((entry = jarInputStream.getNextJarEntry()) != null) {
                        String entryName = entry.getName();

                        if (entryName.startsWith(resourceFolder + "/") && !entry.isDirectory()) {
                            InputStream in = plugin.getResource(entryName);
                            if (in == null) continue;

                            File outFile = new File(plugin.getDataFolder(), entryName);
                            outFile.getParentFile().mkdirs();

                            try (OutputStream out = new FileOutputStream(outFile)) {
                                byte[] buffer = new byte[1024];
                                int length;
                                while ((length = in.read(buffer)) > 0) {
                                    out.write(buffer, 0, length);
                                }
                            }

                            in.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to extract default kits: " + e.getMessage());
            e.printStackTrace();
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
