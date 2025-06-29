package lol.vedant.skypvp.commands.perks;

import lol.vedant.skypvp.menu.PerksMenu;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;

public class PerkCommand {

    @Command(
            name = "perk",
            desc = "Activate or deactivate your unlocked perks",
            aliases = {"perks"},
            senderType = Command.SenderType.PLAYER
    )
    public void execute(CommandArguments args) {
        Player player = args.getSender();

        new PerksMenu(player).open(player);
    }

}
