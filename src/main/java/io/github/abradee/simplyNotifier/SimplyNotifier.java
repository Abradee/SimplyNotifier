package io.github.abradee.simplyNotifier;

import de.clickism.modrinthupdatechecker.ModrinthUpdateChecker;
import io.github.abradee.simplyNotifier.Listeners.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimplyNotifier extends JavaPlugin {


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);

        getLogger().info("The plugin has started.");
        getLogger().info("Feel free to donate through https://patreon.com/abradee");

        new ModrinthUpdateChecker("simplynotifier", "paper", null)
                .checkVersion(latestVersion -> {
                    String currentVersion = getDescription().getVersion();

                    if (currentVersion.contains("-BUILD") || currentVersion.contains("-SNAPSHOT")) {
                        getLogger().warning("Running a development build (" + currentVersion + "). Skipping update check.");
                        getLogger().warning("Development builds are NOT for anything outside of testing.");
                        getLogger().warning("See https://abradee.github.io/devbuild/ for more info.");
                        return;
                    }

                    if (currentVersion.equals(latestVersion)) {
                        getLogger().info("You are running the latest version!");
                    } else {
                        getLogger().warning("A new update is available: v" + latestVersion);
                        getLogger().warning("Your current version: v" + currentVersion);
                        getLogger().warning("Download it here: https://modrinth.com/plugin/simplyquests");
                    }
                });
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has stopped.");
        getLogger().info("Thanks for using SimplyQuests!");
    }
}