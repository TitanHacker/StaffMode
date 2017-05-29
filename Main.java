package me.Timislol12.staffmode;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public HashMap <Player, ItemStack[]> pArmor = new HashMap<Player, ItemStack[]>();
	public HashMap <Player, ItemStack[]> pItems = new HashMap<Player, ItemStack[]>();
	public String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "Staff-Mode" + ChatColor.GRAY + "]";
	public ArrayList<String> staff = new ArrayList<String>();
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventsManager(), this);
	}
	
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		Player p = (Player)sender;
		PlayerInventory pi = p.getInventory();
		
		if(cmd.getName().equalsIgnoreCase("staffmode")) {
			if(!Main.this.staff.contains(p.getName())) {
				p.sendMessage("You're in staff mode over 10 seconds...");
				Main.this.staff.add(p.getName());
				
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					public void run() {
						if(Main.this.staff.contains(p.getName())) {
							Main.this.pArmor.put(p, pi.getArmorContents());
							Main.this.pItems.put(p, pi.getContents());
							pi.setArmorContents(null);
							pi.setContents(null);
							pi.clear();
							p.setGameMode(GameMode.CREATIVE);
							Bukkit.getServer().broadcastMessage(prefix + ChatColor.GREEN + p.getName() + ChatColor.AQUA + "Is now in staff mode!");
						} else {
							p.sendMessage(prefix + ChatColor.AQUA + "Chaning to StaffMode was cancelled");
						}
					}
				}, 200L);
			} else {
				Main.this.staff.remove(p.getName());
				p.sendMessage("You are already in staff mode, I wil get u in normal mode");
				pi.setArmorContents(null);
				pi.setContents(null);
				pi.clear();
				p.setGameMode(GameMode.SURVIVAL);
				if(Main.this.pArmor.containsKey(p)) {
					pi.setArmorContents( (ItemStack[]) Main.this.pArmor.get(p) );
				}
				if (Main.this.pItems.containsKey(p)) {
					pi.setContents( (ItemStack[]) Main.this.pItems.get(p) );
				}
				Bukkit.getServer().broadcastMessage(prefix + ChatColor.GREEN + p.getName() + ChatColor.AQUA + "Changed in to normal mode");
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("staff help")) {
			p.sendMessage(prefix + ChatColor.DARK_GRAY + "Commands");
			p.sendMessage("/staff help - Shows the available commands.");
			p.sendMessage("/staffmode - Change between normal and staffmode");
		}
		return true;
	}
}
