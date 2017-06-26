package me.jeremy.ifpgenblks;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class listener implements Listener{
	static main plugin;
	public listener(main main) {
		plugin = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		try {
			if (plugin.enableAdd) {
				Boolean cont = false;
				List<String> items = plugin.getBlocks().getStringList(plugin.category + ".Blocks");
				int i = -1;
				List<String> items2 = plugin.getBlocks().getStringList(plugin.category + ".Blocks");
				for (String item : items) {
					i++;
					if (item.equals(clicked.getType().toString() + ":" + clicked.getData().getData())){
						cont = true;
						items2.remove(i);
						plugin.pSend(p, "Block Removed " + clicked.getType().toString() + ":" + clicked.getData().getData());
						event.setCancelled(true);
					}
				}
				if (cont) {
					plugin.getBlocks().set(plugin.category + ".Blocks", items2);
					plugin.saveBlocks();
					plugin.reloadBlocks();
				}
				
				if (!cont) {
					List<String> a = plugin.getBlocks().getStringList(plugin.category + ".Blocks");
					a.add(clicked.getType().toString() + ":" + clicked.getData().getData());
					plugin.getBlocks().set(plugin.category + ".Blocks", a);
					plugin.saveBlocks();
					plugin.reloadBlocks();
					plugin.pSend(p, "Block Added - " + clicked.getType().toString() + ":" + clicked.getData().getData());
				}
				event.setCancelled(true);
				
			}
		} catch (NullPointerException e) {
			
		}
				
	}
}
