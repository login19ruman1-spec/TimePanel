package me.purplerift.timepanel;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TimeManager {

    public static final double[] SPEEDS = {0.0, 0.25, 0.5, 1.0, 2.0, 5.0, 10.0};

    private final JavaPlugin plugin;
    private final Map<UUID, Double> speeds = new HashMap<>();
    private BukkitTask task;

    public TimeManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(org.bukkit.GameRule.DO_DAYLIGHT_CYCLE, false);
        }

        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (World world : Bukkit.getWorlds()) {
                double speed = speeds.getOrDefault(world.getUID(), 1.0);
                if (speed <= 0.0) {
                    continue;
                }

                // Minecraft time is measured in ticks. One normal server tick = 1 time tick.
                world.setTime(world.getTime() + speed);
            }
        }, 1L, 1L);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }

        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(org.bukkit.GameRule.DO_DAYLIGHT_CYCLE, true);
        }
    }

    public double getSpeed(World world) {
        return speeds.getOrDefault(world.getUID(), 1.0);
    }

    public void setSpeed(World world, double speed) {
        speeds.put(world.getUID(), speed);
        world.setGameRule(org.bukkit.GameRule.DO_DAYLIGHT_CYCLE, false);
    }

    public void reset(World world) {
        speeds.put(world.getUID(), 1.0);
        world.setGameRule(org.bukkit.GameRule.DO_DAYLIGHT_CYCLE, false);
    }

    public void setDay(World world) {
        world.setTime(1000);
    }

    public void setNight(World world) {
        world.setTime(13000);
    }
}
