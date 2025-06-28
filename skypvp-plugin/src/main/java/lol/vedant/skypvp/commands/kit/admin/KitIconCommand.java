package lol.vedant.skypvp.commands.kit.admin;

import com.cryptomorin.xseries.XItemStack;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.utils.Utils;
import lol.vedant.skypvp.kit.KitManager;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitIconCommand {

    SkyPVP plugin = SkyPVP.getPlugin();
    KitManager manager = plugin.getKitManager();

    @Command(
            name = "kit.seticon",
            aliases = {"kits.seticon"},
            senderType = Command.SenderType.PLAYER,
            permission = "skypvp.kits.admin"
    )
    public void execute(CommandArguments args) {
        Player player = args.getSender();
        ItemStack item;
        if(Utils.getServerVersion() >= 19 ) {
            if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                player.sendMessage(Utils.cc("&cPlease hold a item in hand for making it a display item."));
                return;
            }
            item = player.getInventory().getItemInMainHand();
        } else {
            if(player.getItemInHand().getType() == Material.AIR) {
                player.sendMessage(Utils.cc("&cPlease hold a item in hand for making it a display item."));
                return;
            }
            item = player.getItemInHand();
        }

        String id = args.getArgument(0);

        YamlConfiguration file = plugin.getKitManager().getKitFile(id);
        file.set("kit." + id + ".displayItem", XItemStack.serialize(item));

        plugin.getKitManager().saveKitFile(id, file);

        player.sendMessage(Utils.cc("&aItem set to " + item.getType() + " successfully!"));

        manager.load();


    }

}
