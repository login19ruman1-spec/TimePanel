package me.purplerift.timepanel;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class TimePanelGUI {

    public static final String TITLE = ChatColor.DARK_PURPLE + "Управление временем";

    private final PurpleTimePanel plugin;
    private final TimeManager manager;

    public TimePanelGUI(PurpleTimePanel plugin, TimeManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    public void open(Player player) {
        World world = player.getWorld();
        Inventory inv = Bukkit.createInventory(null, 27, TITLE);

        fill(inv, Material.BLACK_STAINED_GLASS_PANE, " ");

        inv.setItem(4, item(Material.CLOCK, ChatColor.LIGHT_PURPLE + "Текущее время",
                ChatColor.GRAY + "Мир: " + ChatColor.WHITE + world.getName(),
                ChatColor.GRAY + "Скорость: " + ChatColor.WHITE + format(manager.getSpeed(world)) + "x"));

        inv.setItem(10, item(Material.REDSTONE_TORCH, ChatColor.RED + "x0.25",
                ChatColor.GRAY + "Очень медленно"));
        inv.setItem(11, item(Material.ORANGE_DYE, ChatColor.GOLD + "x0.5",
                ChatColor.GRAY + "Медленно"));
        inv.setItem(12, item(Material.CLOCK, ChatColor.YELLOW + "x1",
                ChatColor.GRAY + "Обычная скорость"));
        inv.setItem(13, item(Material.SUGAR, ChatColor.GREEN + "x2",
                ChatColor.GRAY + "Быстро"));
        inv.setItem(14, item(Material.GLOWSTONE_DUST, ChatColor.AQUA + "x5",
                ChatColor.GRAY + "Очень быстро"));
        inv.setItem(15, item(Material.NETHER_STAR, ChatColor.LIGHT_PURPLE + "x10",
                ChatColor.GRAY + "Сверхбыстро"));
        inv.setItem(16, item(Material.BARRIER, ChatColor.DARK_GRAY + "Пауза",
                ChatColor.GRAY + "Полностью остановить время"));

        inv.setItem(20, item(Material.SUNFLOWER, ChatColor.YELLOW + "Установить день",
                ChatColor.GRAY + "Устанавливает время на 1000"));
        inv.setItem(22, item(Material.RED_BED, ChatColor.BLUE + "Установить ночь",
                ChatColor.GRAY + "Устанавливает время на 13000"));
        inv.setItem(24, item(Material.ENDER_EYE, ChatColor.LIGHT_PURPLE + "Сбросить",
                ChatColor.GRAY + "Скорость обратно на x1"));

        player.openInventory(inv);
    }

    private void fill(Inventory inv, Material material, String name) {
        ItemStack item = item(material, name);
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) inv.setItem(i, item);
        }
    }

    private ItemStack item(Material material, String name, String... lore) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        if (lore.length > 0) meta.setLore(List.of(lore));
        stack.setItemMeta(meta);
        return stack;
    }

    private String format(double speed) {
        if (speed == 0) return "Пауза";
        return String.valueOf(speed);
    }
}
