package us.empiremc;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;

public class RestartManager {
    private final KochiRestart plugin;
    private final ConfigManager configManager;
    private Instant nextRestartTime;

    public RestartManager(KochiRestart plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        long restartInterval = configManager.getResetTime(); // Obtiene el tiempo configurado
        this.nextRestartTime = Instant.now().plusMillis(restartInterval);
        startCountdownTask();
    }

    public void startCountdownTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                long timeRemaining = Duration.between(Instant.now(), nextRestartTime).toSeconds();

                if (timeRemaining == 30 || timeRemaining == 20 || timeRemaining == 10) {
                    Bukkit.broadcastMessage("§c¡El End se reiniciará en " + timeRemaining + " segundos! Sal si no quieres morir.");
                }
                if (timeRemaining <= 5 && timeRemaining > 0) {
                    Bukkit.broadcastMessage("§4Reinicio en " + timeRemaining + "...");
                }
                if (timeRemaining <= 0) {
                    executeRestart();
                }
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    public void executeRestart() {
        Bukkit.broadcastMessage("§c¡El End se está reiniciando!");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv delete MundoVacio_the_end");
        Bukkit.getScheduler().runTaskLater(plugin, () ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv confirm"), 40L);
        Bukkit.getScheduler().runTaskLater(plugin, () ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv create MundoVacio_the_end end"), 80L);

        // Mantener el último intervalo configurado
        long restartInterval = configManager.getResetTime();
        this.nextRestartTime = Instant.now().plusMillis(restartInterval);
    }

    public String getFormattedTimeRemaining() {
        Duration remaining = Duration.between(Instant.now(), nextRestartTime);
        long days = remaining.toDays();
        long hours = remaining.toHoursPart();
        long minutes = remaining.toMinutesPart();
        long seconds = remaining.toSecondsPart();

        return String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, seconds);
    }

    public Instant getNextRestartTime() {
        return nextRestartTime;
    }

    public void setRestartTime(long millis) {
        this.nextRestartTime = Instant.now().plusMillis(millis);
        configManager.setResetTime(millis);
    }
}
