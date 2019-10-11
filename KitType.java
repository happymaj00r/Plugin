package myvcrime;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum KitType {
	
	MAP(new ItemStack[]{new ItemStack(Material.MAP)}),
	COMPASS(new ItemStack[]{new ItemStack(Material.COMPASS)}),
	LOCK(new ItemStack[]{new ItemStack(Material.BLAZE_ROD)}),
	CHEST(new ItemStack[]{new ItemStack(Material.CHEST)});
	
	
	ItemStack[] items;
	
	KitType(ItemStack[] items){
		this.items = items;
	}
	
	public ItemStack[] getLoot(){
		return items;
	}
}
