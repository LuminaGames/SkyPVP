package lol.vedant.skypvp.perks.perk;

import lol.vedant.skypvp.api.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedPerk implements Perk {

    private final String name;
    private final long price;

    public SpeedPerk(String name, long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getPrice() {
        return price;
    }

    @Override
    public void apply(Player player) {
        player.getActivePotionEffects().clear();
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
    }

    @Override
    public void remove(Player player) {
        player.getActivePotionEffects().clear();
    }

    @Override
    public boolean isActive(Player player) {
        return player.hasPotionEffect(PotionEffectType.SPEED);
    }
}
