package lol.vedant.skypvp.perks;

import lol.vedant.skypvp.api.perks.Perk;
import lol.vedant.skypvp.perks.perk.BulldozerPerk;
import lol.vedant.skypvp.perks.perk.ExpPerk;
import lol.vedant.skypvp.perks.perk.SpeedPerk;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PerkManager {

    private Map<String, Perk> perks = new HashMap<>();

    public PerkManager() {

        registerPerks(
                new BulldozerPerk("Bulldozer", 3000),
                new ExpPerk("Experience", 5000),
                new SpeedPerk("Speed", 10000)
        );

    }

    public void registerPerks(Perk... perks) {
        Arrays.stream(perks).forEach(p -> this.perks.put(p.getName(), p));
    }

    public Map<String, Perk> getPerks() {
        return this.perks;
    }

    public void applyPerk(Player player, String perk) {
        Perk p = perks.get(perk);
        if(perk != null) {
            p.apply(player);
        }
    }

    public void removePerk(Player player, String perk) {
        Perk p = perks.get(perk);
        if(perk != null) {
            p.remove(player);
        }
    }

}
