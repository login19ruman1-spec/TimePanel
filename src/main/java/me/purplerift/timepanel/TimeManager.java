package me.purplerift.timepanel;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TimeManager {

    public static final double[] SPEEDS = {
            0.0,
            0.25,
            0.5,
            1.0,
            2.0,
            5.0,
            10.0
    };

    private final JavaPlugin plugin;

    private final Map<UUID, Double> speeds = new HashMap<>();
    private final Map<UUID, Double> timeRemainders = new HashMap<>();

    private BukkitTask task;

    public TimeManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            timeRemainders.put(world.getUID(), 0.0);
        }

        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            for (World world : Bukkit.getWorlds()) {

                double speed = speeds.getOrDefault(world.getUID(), 1.0);

                if (speed <= 0.0) {
                    continue;
                }

                UUID worldId = world.getUID();

                double remainder =
                        timeRemainders.getOrDefault(worldId, 0.0);

                remainder += speed;

                long ticksToAdd = (long) remainder;

                remainder -= ticksToAdd;

                timeRemainders.put(worldId, remainder);

                if (ticksToAdd > 0) {
                    long newTime = world.getTime() + ticksToAdd;
                    world.setTime(newTime);
                }
            }

        }, 1L, 1L);
    }

    public void stop() {

        if (task != null) {
            task.cancel();
            task = null;
        }

        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        }
    }

    public double getSpeed(World world) {
        return speeds.getOrDefault(world.getUID(), 1.0);
    }

    public void setSpeed(World world, double speed) {

        speeds.put(world.getUID(), speed);

        timeRemainders.put(world.getUID(), 0.0);

        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    }

    public void reset(World world) {

        speeds.put(world.getUID(), 1.0);

        timeRemainders.put(world.getUID(), 0.0);

        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    }

    public void setDay(World world) {

        world.setTime(1000);

        timeRemainders.put(world.getUID(), 0.0);
    }

    public void setNight(World world) {

        world.setTime(13000);

        timeRemainders.put(world.getUID(), 0.0);
    }
}
