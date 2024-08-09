package lol.vedant.skypvp.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public class SkyPVPExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "skypvp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "COMPHACK";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }


}
