package us.empiremc;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KochiRestartPlaceholder extends PlaceholderExpansion {
    private final RestartManager restartManager;

    public KochiRestartPlaceholder(RestartManager restartManager) {
        this.restartManager = restartManager;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "kochirestart";
    }

    @Override
    public @NotNull String getAuthor() {
        return "EmpireMC";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (identifier.equalsIgnoreCase("time_remaining")) {
            return restartManager.getFormattedTimeRemaining();
        }
        return null;
    }
}
