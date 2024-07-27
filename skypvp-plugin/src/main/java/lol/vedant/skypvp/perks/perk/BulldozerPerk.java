package lol.vedant.skypvp.perks.perk;

import lol.vedant.skypvp.api.events.PlayerKillEvent;
import lol.vedant.skypvp.api.perks.Perk;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BulldozerPerk implements Perk, Listener {

    private final String name;
    private final long price;

    public BulldozerPerk(String name, long price) {
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
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5, 0));
    }

    @Override
    public void remove(Player player) {
        player.getActivePotionEffects().clear();
    }

    @Override
    public boolean isActive(Player player) {
        return player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE);
    }

    @EventHandler
    public void onPlayerKill(PlayerKillEvent e) {
        Player player = e.getPlayer();
        Player killer = e.getKiller();
        apply(killer);
        player.sendMessage(Utils.cc("&aYou got 5 seconds of strength for killing &c" + player.getName()));
    }
}
