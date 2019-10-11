package myvcrime.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import myvcrime.customGUI.IGUI;
import net.minecraft.server.v1_12_R1.IInventory;

public class InventoryEvents implements Listener {
	Plugin plugin;
	
	public InventoryEvents(Plugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e){
		Inventory inv = e.getClickedInventory();	
		CraftInventory craftInventory = (CraftInventory) inv;
		IInventory iInventory = craftInventory.getInventory();
		if(iInventory instanceof IGUI){
			e.setCancelled(true);
			IGUI igui = (IGUI) iInventory;
			Player p = (Player) e.getWhoClicked();
			igui.onClick(p, e.getCurrentItem());
		}
	}
	
	public void removeItemByMaterial(Material mat, Inventory inv, int amount){
		ItemStack[] inventoryContent = inv.getContents();
		for(ItemStack item : inventoryContent){
			if(item == null) continue;
			if(item.getType() == mat){
				if(item.getAmount() < amount){
					amount-= item.getAmount();
					inv.remove(item);
				}
				if(item.getAmount() > amount){
					item.setAmount(item.getAmount() - amount);
					break;
				}
				if(item.getAmount() == amount){
					inv.remove(item);
					break;
				}
			}
		}
	}
}
