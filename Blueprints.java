package myvcrime;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public enum Blueprints {
	
	POWDER_LV1("BLUEPRINT","Special White Powder");
	
	String name;
	String lore;
	
	Blueprints(String name, String lore){
		this.name = name;
		this.lore = lore;
	}
	
	public String getName(){
		return name;
	}

	public String getLore(){
		return lore;
	}
	
	public Blueprints[] getBlueprints(){
		Blueprints[] blueprints = {POWDER_LV1};
		return blueprints;
	}
	
	public ItemStack createBlueprint(){
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		List<String> loreList = new ArrayList<String>();
		loreList.add(lore);
		meta.setLore(loreList);
		meta.setDisplayName(ChatColor.DARK_GREEN + name);
		item.setItemMeta(meta);
		return item;
	}
	
	
}
