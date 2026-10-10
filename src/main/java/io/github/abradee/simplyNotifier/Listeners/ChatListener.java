package io.github.abradee.simplyNotifier.Listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        String message = PlainTextComponentSerializer.plainText().serialize(event.message());

        String[] tokens = message.split("\\s+");
        Set<String> mentionedWords = new HashSet<>();

        for (String token : tokens) {
            String clean = token.replaceAll("[^a-zA-Z0-9_]", "").toLowerCase();
            if (!clean.isEmpty()) {
                mentionedWords.add(clean);
            }
        }

        Plugin plugin = Bukkit.getPluginManager().getPlugin("SimplyNotifier");

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (mentionedWords.contains(onlinePlayer.getName().toLowerCase())) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    onlinePlayer.sendActionBar(Component.text("§aYou were mentioned in chat!"));
                });
            }
        }
    }
}