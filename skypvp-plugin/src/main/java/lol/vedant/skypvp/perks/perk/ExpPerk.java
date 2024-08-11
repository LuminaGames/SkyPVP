package lol.vedant.skypvp.perks.perk;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.events.PlayerKillEvent;
import lol.vedant.skypvp.api.perks.Perk;
import lol.vedant.skypvp.api.perks.PerkType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;

public class ExpPerk implements Perk, Listener {

    private final String name;
    private final long price;
    private static SkyPVP plugin = SkyPVP.getPlugin();

    public ExpPerk(String name, long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PerkType getType() {
        return PerkType.EXPERIENCE;
    }

    @Override
    public long getPrice() {
        return price;
    }

    @Override
    public void apply(Player player) {
        player.giveExpLevels(1);
    }

    @Override
    public void remove(Player player) {
        player.giveExp(-1);
    }

    @Override
    public boolean isActive(Player player) {
        return player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE);
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();

        if(killer == null) {
            return;
        }

        if(SkyPVP.getPlugin().getDb().getActivePerk(killer.getUniqueId()).equals(PerkType.EXPERIENCE)) {
            apply(killer);
        }
    }

}
