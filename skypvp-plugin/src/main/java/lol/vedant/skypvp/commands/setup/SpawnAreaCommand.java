package lol.vedant.skypvp.commands.setup;

import lol.vedant.skypvp.api.item.SelectWand;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;


public class SpawnAreaCommand {

    @Command(
            name = "skypvp.spawnarea",
            desc = "Set the spawn region in the spawn area.",
            permission = "skypvp.commands.admin",
            senderType = Command.SenderType.PLAYER
    )
    public void execute(CommandArguments args) {
        Player player = args.getSender();
        player.getInventory().addItem(new SelectWand());
    }

}
