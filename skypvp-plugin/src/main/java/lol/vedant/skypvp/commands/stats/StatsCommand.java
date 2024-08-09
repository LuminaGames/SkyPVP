package lol.vedant.skypvp.commands.stats;

import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;

public class StatsCommand {

    @Command(
            name = "skypvp.stats",
            aliases = { "stats" },
            desc = "View your or a player's stats",
            senderType = Command.SenderType.PLAYER
    )
    public void execute(CommandArguments args) {
        Player player = args.getSender();


    }
}
