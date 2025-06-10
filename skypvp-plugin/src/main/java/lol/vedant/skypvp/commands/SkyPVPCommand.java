package lol.vedant.skypvp.commands;

import lol.vedant.skypvp.api.config.Message;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;

import static lol.vedant.skypvp.SkyPVP.messages;

public class SkyPVPCommand {

    @Command(
            name = "skypvp",
            permission = "skypvp.command",
            desc = "Main command for the SkyPVP plugin",
            aliases = {"sp"},
            senderType = Command.SenderType.BOTH
    )
    public void execute(CommandArguments args) {
        if(args.getArguments().length == 0) {
            args.getSender().sendMessage(messages.getString(Message.HELP_MESSAGE_1));
        }
    }

}
