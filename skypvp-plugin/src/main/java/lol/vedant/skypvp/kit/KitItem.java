package lol.vedant.skypvp.kit;


import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public enum KitItem {
    WARRIOR(new ItemBuilder(Material.IRON_SWORD)
            .name("§cWarrior Kit")
            .lore("§7A strong kit for melee combat.")
            .flags()
            .build()),
    ARCHER(new ItemBuilder(Material.BOW)
            .name("§aArcher Kit")
            .lore("§7A kit for ranged attacks.")
            .flags()
            .build()),
    TANK(new ItemBuilder(Material.IRON_CHESTPLATE)
            .name("§bTank Kit")
            .lore("§7High defense and durability.")
            .flags()
            .build()),
    ASSASSIN(new ItemBuilder(Material.IRON_SWORD)
            .name("§eAssassin Kit")
            .lore("§7Quick and deadly in close combat.")
            .flags()
            .build()),
    PYRO(new ItemBuilder(Material.FLINT_AND_STEEL)
            .name("§6Pyro Kit")
            .lore("§7Master of fire and flames.")
            .flags()
            .build()),
    SNIPER(new ItemBuilder(Material.CROSSBOW)
            .name("§9Sniper Kit")
            .lore("§7Long-range precision attacks.")
            .flags()
            .build()),
    VAMPIRE(new ItemBuilder(Material.REDSTONE)
            .name("§4Vampire Kit")
            .lore("§7Drain the life force of your enemies.")
            .flags()
            .build()),
    KNIGHT(new ItemBuilder(Material.DIAMOND_SWORD)
            .name("§3Knight Kit")
            .lore("§7A balanced kit for noble warriors.")
            .flags()
            .build()),
    NINJA(new ItemBuilder(Material.ENDER_PEARL)
            .name("§8Ninja Kit")
            .lore("§7Stealth and agility at your fingertips.")
            .flags()
            .build()),
    THOR(new ItemBuilder(Material.TRIDENT)
            .name("§bThor Kit")
            .lore("§7Wield the power of thunder and lightning.")
            .flags()
            .build()),
    HEALER(new ItemBuilder(Material.GOLDEN_APPLE)
            .name("§dHealer Kit")
            .lore("§7Support your allies with healing abilities.")
            .flags()
            .build());

    private final ItemStack item;

    KitItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}
