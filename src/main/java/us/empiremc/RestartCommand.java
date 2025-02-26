package us.empiremc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RestartCommand implements CommandExecutor {
    private final RestartManager restartManager;

    public RestartCommand(RestartManager restartManager) {
        this.restartManager = restartManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usa /kochiRestart info | time <tiempo> | test");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "info":
                sender.sendMessage(ChatColor.GREEN + "Tiempo restante para el reinicio: " +
                        ChatColor.YELLOW + restartManager.getFormattedTimeRemaining());
                return true;

            case "time":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Uso: /kochiRestart time <1m/5h/2d/1mo>");
                    return true;
                }

                long newTime = TimeParser.parseTime(args[1]);
                if (newTime > 0) {
                    restartManager.setRestartTime(newTime);
                    sender.sendMessage(ChatColor.GREEN + "Tiempo de reinicio cambiado a: " +
                            ChatColor.YELLOW + args[1]);
                } else {
                    sender.sendMessage(ChatColor.RED + "Formato inválido. Usa: 1m, 5h, 2d, 1mo.");
                }
                return true;

            case "test":
                restartManager.executeRestart();
                return true;

            default:
                sender.sendMessage(ChatColor.RED + "Comando desconocido.");
                return true;
        }
    }
}
