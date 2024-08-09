package lol.vedant.skypvp.perks;

import lol.vedant.skypvp.api.perks.Perk;
import lol.vedant.skypvp.api.perks.PerkType;
import lol.vedant.skypvp.perks.perk.BulldozerPerk;
import lol.vedant.skypvp.perks.perk.ExpPerk;
import lol.vedant.skypvp.perks.perk.SpeedPerk;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static lol.vedant.skypvp.SkyPVP.config;

public class PerkManager {

    private Map<PerkType, Perk> perks = new HashMap<>();

    public PerkManager() {

        registerPerks(
                new BulldozerPerk("Bulldozer", config.getInt("perks.bulldozer.price")),
                new ExpPerk("Experience", config.getInt("perks.experience.price")),
                new SpeedPerk("Speed", config.getInt("perks.speed.price"))
        );

    }

    public void registerPerks(Perk... perks) {
        Arrays.stream(perks).forEach(p -> this.perks.put(p.getType(), p));
    }

    public Map<PerkType, Perk> getPerks() {
        return this.perks;
    }

    public Perk getPerk(String name) {
        for (Map.Entry<PerkType, Perk> entry : perks.entrySet()) {
            Perk perk = entry.getValue();
            if (perk.getName().equalsIgnoreCase(name)) {
                return perk;
            }
        }
        return null;
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
