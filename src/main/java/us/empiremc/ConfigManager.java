package us.empiremc;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final KochiRestart plugin;
    private static final String RESET_TIME_KEY = "reset-time";

    public ConfigManager(KochiRestart plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public long getResetTime() {
        return plugin.getConfig().getLong(RESET_TIME_KEY, 86400000); // 24 horas por defecto
    }

    public void setResetTime(long time) {
        FileConfiguration config = plugin.getConfig();
        config.set(RESET_TIME_KEY, time);
        plugin.saveConfig();
    }
}
