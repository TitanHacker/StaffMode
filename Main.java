package me.Timislol12.staffmode;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin { // removed the Listener implementation as there are no events in this class.

	public HashMap <Player, ItemStack[]> pArmor = new HashMap<Player, ItemStack[]>();
	public HashMap <Player, ItemStack[]> pItems = new HashMap<Player, ItemStack[]>();
	public String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "Staff-Mode" + ChatColor.GRAY + "]";
	private  ArrayList<Player> vanished = new ArrayList<Player>();
	public ArrayList<String> staff = new ArrayList<String>();
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventsManager(), this);
		// There were no events in this class, so I removed the event register.
		registerCommands();
	}

	public void onDisable() { // It might be a good idea to clear ArrayLists onDisable to prevent data leaks.
		
	}
	
	// I changed this method's name to follow camelCase naming conventions.
	private void registerCommands() {
		getCommand("report").setExecutor(new Report());
		getCommand("staffchat").setExecutor(new staffchat());
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		Player p = (Player)sender;
		PlayerInventory pi = p.getInventory();
		
		if(cmd.getName().equalsIgnoreCase("staffmode")) {
			if(!Main.this.staff.contains(p.getName())) {
				p.sendMessage(prefix + ChatColor.AQUA + " You're now in staffmode");
				
				// I don't think you need to do "Main.this"
				// Instead, you can just stick with "staff.add"
				Main.this.staff.add(p.getName());
				
				for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
					pl.hidePlayer(p);
				} vanished.add(p);
				
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					public void run() {
						if(Main.this.staff.contains(p.getName())) {
							Main.this.pArmor.put(p, pi.getArmorContents());
							Main.this.pItems.put(p, pi.getContents());
							pi.setArmorContents(null);
							pi.clear();
							p.setGameMode(GameMode.CREATIVE);
							
							
							ItemStack stack = new ItemStack(Material.GRASS);
							ItemMeta stackMeta = stack.getItemMeta();
							stackMeta.setDisplayName(ChatColor.AQUA + "GameMode Changer");
							stack.setItemMeta(stackMeta);
							
							
							
							ItemStack grass = new ItemStack(Material.COMPASS);
							ItemMeta stackMeta1 = grass.getItemMeta();
							stackMeta1.setDisplayName(ChatColor.AQUA + "Random Teleport");
							grass.setItemMeta(stackMeta1);
							
							@SuppressWarnings("deprecation")
							ItemStack vanishon = new ItemStack(351, 1, (short) 10);
							ItemMeta vanishMeta = vanishon.getItemMeta();
							vanishMeta.setDisplayName(ChatColor.AQUA + "Vanish On");
							vanishon.setItemMeta(vanishMeta);
							
							@SuppressWarnings("deprecation")
							ItemStack vanishoff = new ItemStack(351, 1, (short) 1);
							ItemMeta vanishMeta2 = vanishoff.getItemMeta();
							vanishMeta2.setDisplayName(ChatColor.AQUA + "Vanish Off");
							vanishoff.setItemMeta(vanishMeta2);
							
							
							p.getInventory().setItem(7, vanishoff); //vanishoff//
							p.getInventory().setItem(8, vanishon); // vanishon //
							p.getInventory().setItem(1, stack); // compass //
							p.getInventory().setItem(3, grass); // grass //
							
							
							
							Bukkit.getServer().broadcastMessage(prefix + ChatColor.GREEN +  " " + p.getName() + ChatColor.AQUA + " Is now in staff mode!");
						} else {
							p.sendMessage(prefix + ChatColor.AQUA + " Chaning to StaffMode was cancelled");
						}
					}
				}, 0L); // If it's 0L, couldn't you just do a repeating task?
			} else {
				Main.this.staff.remove(p.getName());
				p.sendMessage("You are already in staff mode, I wil get you in normal mode");
				pi.setArmorContents(null);
				pi.clear();
				p.setGameMode(GameMode.SURVIVAL);
				
				
				for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
				pl.showPlayer(p);
				} vanished.remove(p);
				
				if(Main.this.pArmor.containsKey(p)) {
					pi.setArmorContents( (ItemStack[]) Main.this.pArmor.get(p) );
				}
				if (Main.this.pItems.containsKey(p)) {
					pi.setContents( (ItemStack[]) Main.this.pItems.get(p) );
				}
				Bukkit.getServer().broadcastMessage(prefix + ChatColor.GREEN + " " + p.getName() + ChatColor.AQUA + " Changed in to normal mode");
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("staff")) {
			p.sendMessage(prefix + ChatColor.DARK_GRAY + " Commands");
			p.sendMessage("/staff help - Shows the available commands.");
			p.sendMessage("/staffmode - Change between normal and staffmode");
		}
		return true;
	}
}
