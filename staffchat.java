package me.Timislol12.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class staffchat implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args) {
		if (s instanceof Player){
			Player p = (Player) s;
			if (args.length == 0) {
				return true;
			}
			
			if (args.length > 0) {
				StringBuilder b = new StringBuilder();
				for (int i = 0; i < args.length; i++){
					b.append(" ");
					b.append(args[i]);
			
				}
				String message = b.toString();
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
				if (all.hasPermission("staffstrike.staffchat") || all.isOp()) {
					all.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Staff-Chat" + ChatColor.GRAY + "] " 
				+ ChatColor.GOLD + p.getDisplayName() +  ": " + ChatColor.WHITE + message);
				}
			}
			}
		}
		return true;
	}
}
