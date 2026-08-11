package me.purplerift.timepanel;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;

public final class TimePanelCommand implements CommandExecutor, TabCompleter {

    private final PurpleTimePanel plugin;
    private final TimeManager manager;

    public TimePanelCommand(PurpleTimePanel plugin, TimeManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Эту команду можно использовать только в игре.");
            return true;
        }

        if (!player.hasPermission("purpletimes.panel")) {
            player.sendMessage(ChatColor.RED + "У вас нет прав.");
            return true;
        }

        plugin.openPanel(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of();
    }
}
