package lol.vedant.skypvp.commands.kit.admin;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.utils.Utils;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class KitPriceCommand {

    SkyPVP plugin = SkyPVP.getPlugin();

    @Command(
            name = "kit.price",
            aliases = {"kits.price"},
            senderType = Command.SenderType.PLAYER,
            permission = "skypvp.kits.admin"
    )
    public void setPrice(CommandArguments args) {
        Player player = args.getSender();
        String kitId = args.getArgument(0);
        int price = 0;

        if(args.getArgument(0) == null) {
            player.sendMessage(Utils.cc("&cPlease specify a Kit ID"));
            return;
        }

        if(args.getArgument(1) == null) {
            player.sendMessage(Utils.cc("&cPlease specify an amount"));
            return;
        }

        try {
            price = Integer.parseInt(args.getArgument(1));
        } catch (NumberFormatException e) {
            player.sendMessage(Utils.cc("Please specify a numerical value"));
            return;
        }

        YamlConfiguration file = plugin.getKitManager().getKitFile(kitId);
        file.set("kit." + kitId + ".price", price);

        plugin.getKitManager().saveKitFile(kitId, file);

        player.sendMessage(Utils.cc("&aPrice set to " + price + " successfully!"));

        plugin.getKitManager().load();
    }

}
