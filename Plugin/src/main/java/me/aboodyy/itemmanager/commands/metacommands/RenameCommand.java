/*

    ItemManager - A plugin to manage items in player's inventory.
    Copyright (C) 2019 aBooDyy

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package me.aboodyy.itemmanager.commands.metacommands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import me.aboodyy.itemmanager.commands.ItemManagerCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import static me.aboodyy.itemmanager.utils.ItemUtils.getItemInHand;
import static me.aboodyy.itemmanager.utils.Messages.color;
import static me.aboodyy.itemmanager.utils.Utils.*;

@CommandAlias("itemmanager|imanager|im")
public class RenameCommand extends ItemManagerCommand {

    @Subcommand("rename|rn")
    @CommandCompletion("<name> @players")
    @CommandPermission("itemmanager.rename")
    public void onRename(CommandSender sender, String[] args) {
        String space = getConfig().getString("symbols.space", "_");

        if (args.length == 0 || !(sender instanceof Player ) && args.length != 2) {
            sender.sendMessage(color("&cIncorrect usage. &7/ItemManager rename <newName> [player]"));
            return;
        }

        Player p = args.length == 2 ? Bukkit.getPlayerExact(args[1]) : (Player) sender;
        if (p == null) {
            sender.sendMessage(color("&f" + args[1] + " &cis not online."));
            return;
        }
        if (getItemInHand(p) == null || getItemInHand(p).getType() == Material.AIR) {
            sender.sendMessage(color( "&f" + p.getName() + " &cisn't holding an item."));
            return;
        }

        args[0] = args[0].replace(space, " ");
        ItemMeta meta = getItemInHand(p).getItemMeta();
        meta.setDisplayName(color(args[0]));
        getItemInHand(p).setItemMeta(meta);
        sender.sendMessage(color("&aItem name has been changed successfully to &f" + args[0] + " &afor &f" + p.getName()));
    }

}
