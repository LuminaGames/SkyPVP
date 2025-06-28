package lol.vedant.skypvp.commands.kit.admin;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.kit.KitManager;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.configuration.file.YamlConfiguration;

public class KitNameCommand {

    KitManager manager = SkyPVP.getPlugin().getKitManager();

    @Command(
            name = "kit.name",
            aliases = {"kits.name"},
            desc = "Set a display name for a kit",
            permission = "skypvp.kits.admin",
            senderType = Command.SenderType.PLAYER
    )
    public void displayName(CommandArguments args) {

        String kitId = args.getArgument(0);
        String displayName = args.getArgument(1);

        YamlConfiguration config = manager.getKitFile(kitId);
        config.set("kit." + kitId + ".displayName", displayName);
        manager.saveKitFile(kitId, config);

        manager.load();
    }
}
