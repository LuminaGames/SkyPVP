package lol.vedant.skypvp.commands.admin.setup;

import lol.vedant.skypvp.api.config.ConfigPath;
import lol.vedant.skypvp.api.utils.Utils;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;

import static lol.vedant.skypvp.SkyPVP.config;

public class SetSpawnCommand {

    @Command(
            name = "skypvp.setspawn",
            desc = "Set the spawn of the arena",
            permission = "skypvp.command.admin",
            senderType = Command.SenderType.PLAYER
    )
    public void execute(CommandArguments args) {
        Player player = (Player) args.getSender();

        config.set(ConfigPath.ARENA_SPAWN, Utils.parseLocation(player.getLocation()));
        config.save();
    }
}
