package me.Timislol12.staffmode;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class EventsManager implements Listener {

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
}
