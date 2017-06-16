package me.Timislol12.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Report implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label,
		String[] args) {
		Player p = (Player)sender;
		
		if (!sender.hasPermission("staffstrike.report")) {
			sender.sendMessage(ChatColor.AQUA + "You " + ChatColor.RED + "do not have permissions to use this command!");
			return true;	
		}
        if (args.length == 0) {
			
			sender.sendMessage(ChatColor.RED + "You do not Have enough arguments! " + ChatColor.AQUA + "Usage /report <player>");
			return true;
        }
        if (args.length == 1) {
			@SuppressWarnings("deprecation")
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.RED + "Your target is invalid!");
				return true;
			}
			sender.sendMessage(ChatColor.AQUA + "You reported " + ChatColor.RED + target.getName() + ChatColor.AQUA + " thanks for the report.") ;
			target.sendMessage("Reported you! " + sender.getName());
			target.getLocation().getWorld().strikeLightning(p.getLocation());
			return true;
	}
        if (args.length > 1) {
			
			sender.sendMessage(ChatColor.RED + "You have to many arguments! ");
			return true;
        }
        return false;
	}
}
