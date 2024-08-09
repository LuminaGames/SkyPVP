package lol.vedant.skypvp.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKillEvent extends Event {

    public final Player player;
    public final Player killer;
    public static HandlerList handlerList = new HandlerList();

    public PlayerKillEvent(Player player, Player killer) {
        this.player = player;
        this.killer = killer;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getKiller() {
        return killer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
