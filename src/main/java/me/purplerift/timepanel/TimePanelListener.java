package me.purplerift.timepanel;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public final class TimePanelListener implements Listener {

    private final PurpleTimePanel plugin;
    private final TimeManager manager;

    public TimePanelListener(PurpleTimePanel plugin, TimeManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!TimePanelGUI.TITLE.equals(event.getView().getTitle())) return;

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        var world = player.getWorld();

        switch (event.getRawSlot()) {
            case 10 -> manager.setSpeed(world, 0.25);
            case 11 -> manager.setSpeed(world, 0.5);
            case 12 -> manager.setSpeed(world, 1.0);
            case 13 -> manager.setSpeed(world, 2.0);
            case 14 -> manager.setSpeed(world, 5.0);
            case 15 -> manager.setSpeed(world, 10.0);
            case 16 -> manager.setSpeed(world, 0.0);
            case 20 -> manager.setDay(world);
            case 22 -> manager.setNight(world);
            case 24 -> manager.reset(world);
            default -> { return; }
        }

        player.sendMessage(ChatColor.LIGHT_PURPLE + "Время: " +
                ChatColor.WHITE + (manager.getSpeed(world) == 0 ? "Пауза" : manager.getSpeed(world) + "x"));

        plugin.openPanel(player);
    }
}
