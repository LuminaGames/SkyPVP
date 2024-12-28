package lol.vedant.skypvp.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CoinDeductEvent extends Event {

    public HandlerList handlerList = new HandlerList();
    private final Player player;
    private final double amount;
    private final String reason;

    public CoinDeductEvent(Player player, double amount, String reason) {
        this.player = player;
        this.amount = amount;
        this.reason = reason;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public double getAmount() {
        return amount;
    }
}
