package lol.vedant.skypvp.commands.admin;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.utils.Utils;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;

public class BuildModeCommand {

    private SkyPVP plugin = SkyPVP.getPlugin();

    @Command(
            name="skypvp.build",
            desc = "Toggle the build mode.",
            senderType = Command.SenderType.PLAYER
    )
    public void execute(CommandArguments args) {
        Player player = args.getSender();

        if (plugin.getGameManager().hasBuildMode(player)) {
            player.sendMessage(Utils.cc("&cYou have turned off build mode."));
            plugin.getGameManager().enableBuildMode(player);
        }  else {
            player.sendMessage(Utils.cc("&aYou have enabled build mode."));
            plugin.getGameManager().enableBuildMode(player);
        }
    }
}
