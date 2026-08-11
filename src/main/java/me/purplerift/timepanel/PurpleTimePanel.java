package me.purplerift.timepanel;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class PurpleTimePanel extends JavaPlugin {

    private TimeManager timeManager;

    @Override
    public void onEnable() {
        timeManager = new TimeManager(this);
        getServer().getPluginManager().registerEvents(new TimePanelListener(this, timeManager), this);

        TimePanelCommand command = new TimePanelCommand(this, timeManager);
        getCommand("timepanel").setExecutor(command);
        getCommand("timepanel").setTabCompleter(command);

        timeManager.start();
        getLogger().info("PurpleTimePanel enabled.");
    }

    @Override
    public void onDisable() {
        if (timeManager != null) {
            timeManager.stop();
        }
    }

    public void openPanel(org.bukkit.entity.Player player) {
        new TimePanelGUI(this, timeManager).open(player);
    }

    public static String worldKey(World world) {
        return world.getUID().toString();
    }
}
