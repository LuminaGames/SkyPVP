package lol.vedant.skypvp.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CoinReceiveEvent extends Event {

    private final HandlerList handlerList = new HandlerList();
    private final Player player;
    private final double amount;
    private final String reason;

    public CoinReceiveEvent(Player player, double amount, String reason) {
        this.player = player;
        this.amount = amount;
        this.reason = reason;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
