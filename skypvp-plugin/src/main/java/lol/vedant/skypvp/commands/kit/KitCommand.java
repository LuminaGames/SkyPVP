package lol.vedant.skypvp.commands.kit;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.Kit;
import lol.vedant.skypvp.kit.KitManager;
import lol.vedant.skypvp.menu.KitPreviewMenu;
import lol.vedant.skypvp.menu.KitsMenu;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;

public class KitCommand {

    private SkyPVP plugin;
    private KitManager manager = SkyPVP.getPlugin().getKitManager();
    public KitCommand(SkyPVP plugin) {
        this.plugin = plugin;
    }

    @Command(
            name = "kit",
            aliases = {"kits"},
            permission = "skypvp.kits.admin",
            desc = "Buy or Preview kits in the SkyPvP gamemode",
            senderType = Command.SenderType.PLAYER
    )
    public void execute(CommandArguments args) {
        Player player = args.getSender();

        if(plugin.getKitManager().getLoadedKits().isEmpty()) {
            args.getSender().sendMessage(Component.text("<red>There are no kits loaded in the plugin").content());
            return;
        }

        new KitsMenu(plugin.getKitManager().getLoadedKits(), 1).open(player);
    }

    @Command(
            name = "kit.displayname",
            aliases = {"kits.displayname"},
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

    @Command(
            name = "kit.preview",
            aliases = {"kits.preview"},
            desc = "Preview a kit",
            senderType = Command.SenderType.PLAYER
    )
    public void preview(CommandArguments args) {
        String id = args.getArgument(0);
        Player player = args.getSender();
        player.sendMessage("Showing preview for " + id);
        Kit kit = manager.getKitById(id);

        new KitPreviewMenu(kit).open(args.getSender());

    }

}
