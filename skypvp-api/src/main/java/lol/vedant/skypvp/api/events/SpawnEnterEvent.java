package lol.vedant.skypvp.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpawnEnterEvent extends Event {

    private final HandlerList handlerList = new HandlerList();
    private final Player player;

    public SpawnEnterEvent(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }
}