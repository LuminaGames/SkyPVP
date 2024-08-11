package lol.vedant.skypvp.perks.perk;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.perks.Perk;
import lol.vedant.skypvp.api.perks.PerkType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedPerk implements Perk, Listener {

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
    public PerkType getType() {
        return PerkType.SPEED;
    }

    @Override
    public long getPrice() {
        return price;
    }

    @Override
    public void apply(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1));
    }

    @Override
    public void remove(Player player) {
        player.getActivePotionEffects().clear();
    }

    @Override
    public boolean isActive(Player player) {
        return player.hasPotionEffect(PotionEffectType.SPEED);
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player player = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();

        if(killer == null) {
            return;
        }

        if(SkyPVP.getPlugin().getDb().getActivePerk(killer.getUniqueId()).equals(PerkType.SPEED)) {
            apply(killer);
        }
    }
}
