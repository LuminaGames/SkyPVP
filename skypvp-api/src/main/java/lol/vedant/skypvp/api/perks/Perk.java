package lol.vedant.skypvp.api.perks;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Perk {

    String getName();
    PerkType getType();
    long getPrice();
    void apply(Player player);
    void remove(Player player);
    boolean isActive(Player player);
    ItemStack getDisplayItem();


}
