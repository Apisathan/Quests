package com.leonardobishop.quests.bukkit.command;

import com.leonardobishop.quests.bukkit.BukkitQuestsPlugin;
import com.leonardobishop.quests.bukkit.util.CommandUtils;
import com.leonardobishop.quests.bukkit.util.Messages;
import com.leonardobishop.quests.common.player.QPlayer;
import com.leonardobishop.quests.common.player.questprogressfile.QuestProgressFile;
import com.leonardobishop.quests.common.quest.Quest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class AdminModdataCompleteCommandHandler implements CommandHandler {

    private final BukkitQuestsPlugin plugin;

    public AdminModdataCompleteCommandHandler(BukkitQuestsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        if (args.length > 4) {
            QPlayer qPlayer = CommandUtils.getOtherPlayer(sender, args[3], plugin);
            if (qPlayer == null) return;
            QuestProgressFile questProgressFile = qPlayer.getQuestProgressFile();
            Quest quest = plugin.getQuestManager().getQuestById(args[4]);
            if (quest == null) {
                sender.sendMessage(Messages.COMMAND_QUEST_START_DOESNTEXIST.getMessage().replace("{quest}", args[4]));
                return;
            }
            qPlayer.completeQuest(quest);
            plugin.getPlayerManager().savePlayerSync(qPlayer.getPlayerUUID(), questProgressFile);
            sender.sendMessage(Messages.COMMAND_QUEST_ADMIN_COMPLETE_SUCCESS.getMessage().replace("{player}", args[3]).replace("{quest}", quest.getId()));

            if (Bukkit.getPlayer(qPlayer.getPlayerUUID()) == null) {
                plugin.getPlayerManager().dropPlayer(qPlayer.getPlayerUUID());
            }
            return;
        }

        sender.sendMessage(ChatColor.RED + "/quests a/admin moddata complete <player> <quest>");
    }


    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 4) {
            return null;
        } else if (args.length == 5) {
            return TabHelper.tabCompleteQuests(args[4]);
        }
        return Collections.emptyList();
    }

    @Override
    public @Nullable String getPermission() {
        return "quests.admin";
    }
}
