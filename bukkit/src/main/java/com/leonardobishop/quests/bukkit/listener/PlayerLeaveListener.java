package com.leonardobishop.quests.bukkit.listener;

import com.leonardobishop.quests.bukkit.BukkitQuestsPlugin;
import com.leonardobishop.quests.common.player.QPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    private final BukkitQuestsPlugin plugin;

    public PlayerLeaveListener(BukkitQuestsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        plugin.getPlayerManager().removePlayer(event.getPlayer().getUniqueId());
    }

}
