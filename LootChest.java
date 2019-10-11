package myvcrime;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import myvcrime.customEvents.PlayerOpenedChestEvent;
import myvcrime.quests.ConsumableQuestObjective;
import myvcrime.quests.QuestType;
import net.md_5.bungee.api.ChatColor;

public class LootChest {
	
	Plugin plugin;
	
	LootChest(Plugin plugin){
		this.plugin = plugin;
	}
	
	public ItemStack createLootChest(ChestType type){
		List<String> list = new ArrayList<String>();
		list.add(type.getDescription());
		ItemStack is = new ItemBuilder(type.getMaterial(),type.getName(),list).getItem();
		return is;
	}
	
	public static ItemStack createChest(ChestType type){
		List<String> list = new ArrayList<String>();
		list.add(type.getDescription());
		ItemStack is = new ItemBuilder(type.getMaterial(),type.getName(),list).getItem();
		return is;
	}
	
	public static int zufall(int low, int high){
		return (int)(Math.random() * (high - low) + low);
	}
	
	public Boolean isLootChest(ItemStack item){
		if(item.hasItemMeta()){
		String displayName = item.getItemMeta().getDisplayName();
		if(!item.getItemMeta().hasDisplayName())
			return false;
		for(ChestType c : ChestType.getChests()){
			if(displayName.equalsIgnoreCase(c.getName())){
				return true;
			}
		}
		return false;
		} else {
			return false;
		}
	}
	
	public void openChest(Player p, ItemStack item){
		if(!this.isLootChest(item)){
			return;
		}
		String displayName = item.getItemMeta().getDisplayName();
		Inventory inv = p.getInventory();
		int size = inv.getSize();
		for(int i = 0; i < 4; i++){
			if(inv.contains(5 + i)){
				
			}else{
				size--;
			}
		}
		int freeSlots = Utility.getFreeSlotsOfInventory(p);
		for(ChestType c : ChestType.getChests()){
			String typeName = c.getName();
			if(ChatColor.stripColor(typeName).equalsIgnoreCase(ChatColor.stripColor(displayName))){
				int rnd = zufall(1,100);
				Material[] loot = null;
				if(rnd <= 70) loot = c.commonLoot;
				if(rnd > 70 && rnd <= 90)loot = c.rareLoot;
				if(rnd > 90 && rnd <= 99) loot = c.epicLoot;
				if(rnd == 99) loot = c.legendaryLoot;	
				
				int whichItem = zufall(0,loot.length);
				ItemStack[] fixedLoot = c.getFixedLoot();
				int amountOfItems = 0;
				amountOfItems+=fixedLoot.length + 1;
				if(amountOfItems-1 <= freeSlots){
					inv.removeItem(new ItemStack[]{item});
					inv.addItem(new ItemBuilder(loot[whichItem]).getItem());
					if(fixedLoot.length != 0){
						inv.addItem(fixedLoot);
					}
					Utility.playTotemEffect(p);
					
					// QUESTS
					plugin.getServer().getPluginManager().callEvent(new PlayerOpenedChestEvent(p,c));
		      		  
				} else {
					p.sendMessage(ChatColor.RED + "Nicht genügend Platz im Inventar!");
				}
			}
		}
	}
	
}
