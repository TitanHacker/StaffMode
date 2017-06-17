package me.Timislol12.staffmode;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EventsManager implements Listener {
	
	// Before we start, please use this as a learning opportunity rather than copy + pasting. I also wrote this in GitHub, so spacing may be messed up.
	
	// In Java 8, you don't need to do <Player> again.
	// Also - consider using an ArrayList of UUIDs rather than Players, 
	// as that can cause data leaks and bad stuff. UUIDs may be a bit
	// more tedious, but it only takes you 30 more seconds.
	private ArrayList<Player> vanished = new ArrayList<>(); 

	// This is how I do my Main class instances - you don't have to do this.
	private Main plugin;
	
	public EventsManager(Main instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onFoodChange (FoodLevelChangeEvent e) {
		Player p = (Player) e.getEntity();
		if (plugin.staff.contains(p)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDropItem (PlayerDropItemEvent e) {
		Player p = (Player) e.getPlayer();
		if (plugin.staff.contains(p.getName())) {
			e.setCancelled(true);
			p.sendMessage(plugin.prefix + ChatColor.DARK_RED + "You are not able to drop items in Staff-Mode");
		}
	} 

	@EventHandler
	public void onMoveItem (InventoryMoveItemEvent e) {
		Player p = (Player) e.getInitiator();
		if(plugin.staff.contains(p.getName())) {
			e.setCancelled(true);
			p.sendMessage(plugin.prefix + ChatColor.DARK_RED + "You are not able to move items in Staff-Mode");
		}
	}
	@EventHandler // Naming conventions aren't really mandatory, but I changed this to 'onPlayerInteract'
	public void onPlayerInteract(PlayerInteractEvent e) { // I basically just merged all four of your events. Looks a lot simpler and efficient right?
		 if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK ) {
			Player p = e.getPlayer();
			ItemStack stack = p.getItemInHand();
			if(stack != null &&stack.getType() == Material.GRASS && stack.hasItemMeta() && stack.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "GameMode Changer")){
			if(p.getGameMode().equals(GameMode.SURVIVAL)) {
				p.setGameMode(GameMode.CREATIVE);
			}else{
				p.setGameMode(GameMode.SURVIVAL);
			}
			}
			 
			if(stack != null &&stack.getType() == Material.COMPASS && stack.hasItemMeta() && stack.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Random Teleport")){
				ArrayList<Player> players = new ArrayList<Player>();
				for (Player e1 : Bukkit.getOnlinePlayers()) players.add(e1);
				Player randomPlayer = players.get(new Random().nextInt(players.size()));
				p.teleport(randomPlayer.getLocation());
			}
			 
			if(stack != null &&stack.getDurability() == 10 && stack.hasItemMeta() && stack.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Vanish On")){
				p.sendMessage(ChatColor.AQUA + "You are now in vanish!");
				for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
					pl.hidePlayer(p);
					} vanished.add(p);
					
			}
			 
			 if(stack != null &&stack.getDurability() == 1 && stack.hasItemMeta() && stack.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Vanish Off")){
				p.sendMessage(ChatColor.AQUA + "You are now unvanished!");
				for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
					pl.showPlayer(p);
				} vanished.remove(p);
			}
		 }
	}
}
