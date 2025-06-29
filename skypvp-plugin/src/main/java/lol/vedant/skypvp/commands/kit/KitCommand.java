package lol.vedant.skypvp.commands.kit;

import com.cryptomorin.xseries.XItemStack;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.kit.Kit;
import lol.vedant.skypvp.api.utils.Utils;
import lol.vedant.skypvp.kit.KitManager;
import lol.vedant.skypvp.menu.KitPreviewMenu;
import lol.vedant.skypvp.menu.KitsMenu;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


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
            args.getSender().sendMessage(Utils.cc("&cThere are no loaded kits in the plugin"));
            return;
        }

        new KitsMenu(player, plugin.getKitManager().getLoadedKits(), 1).open(player);
    }








}
