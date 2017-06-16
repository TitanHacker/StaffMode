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
	private  ArrayList<Player> vanished = new ArrayList<Player>();
	private Main plugin = Main.getPlugin(Main.class);
	
	
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
	@EventHandler
	public void onClick1(PlayerInteractEvent e) {
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
		 }
	}
	@EventHandler
	public void onClick2(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK ) {
			Player p = e.getPlayer();
			ItemStack stack2 = p.getItemInHand();
			if(stack2 != null &&stack2.getType() == Material.COMPASS && stack2.hasItemMeta() && stack2.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Random Teleport")){
				ArrayList<Player> players = new ArrayList<Player>();
				for (Player e1 : Bukkit.getOnlinePlayers()) players.add(e1);
				Player randomPlayer = players.get(new Random().nextInt(players.size()));
				p.teleport(randomPlayer.getLocation());
			}
		}
		
	}
	@EventHandler
	public void onClick3(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK ) {
			Player p = e.getPlayer();
			ItemStack stack2 = p.getItemInHand();
			if(stack2 != null &&stack2.getDurability() == 10 && stack2.hasItemMeta() && stack2.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Vanish On")){
				p.sendMessage(ChatColor.AQUA + "You are now in vanish!");
				for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
					pl.hidePlayer(p);
					} vanished.add(p);
					
			}
		}
	}
	public void onClick4(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK ) {
			Player p = e.getPlayer();
			ItemStack stack2 = p.getItemInHand();
			if(stack2 != null &&stack2.getDurability() == 1 && stack2.hasItemMeta() && stack2.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Vanish Off")){
				p.sendMessage(ChatColor.AQUA + "You are now unvanished!");
				for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
					pl.showPlayer(p);
				} vanished.remove(p);
			}
		}
	}
}
