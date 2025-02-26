package us.empiremc;

import org.bukkit.plugin.java.JavaPlugin;

public class KochiRestart extends JavaPlugin {
    private ConfigManager configManager;
    private RestartManager restartManager;

    @Override
    public void onEnable() {
        // Inicializar la configuración y el administrador de reinicio
        this.configManager = new ConfigManager(this);
        this.restartManager = new RestartManager(this, configManager);

        // Registrar comandos
        getCommand("kochiRestart").setExecutor(new RestartCommand(restartManager));

        // Registrar PlaceholderAPI si está disponible
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new KochiRestartPlaceholder(restartManager).register();
            getLogger().info("PlaceholderAPI detectado y hookeado correctamente.");
        } else {
            getLogger().warning("PlaceholderAPI no encontrado. Los placeholders no funcionarán.");
        }

        getLogger().info("KochiRestart activado correctamente.");
    }

    @Override
    public void onDisable() {
        getLogger().info("KochiRestart desactivado.");
    }

    public RestartManager getRestartManager() {
        return restartManager;
    }
}
