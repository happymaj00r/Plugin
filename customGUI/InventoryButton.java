package myvcrime.customGUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryButton implements IButton {

	private ItemStack is;
	
	public InventoryButton(ItemStack is){
		this.is = is;
	}
	
	@Override
	public void click(Player p) {
	}

	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return is.getType();
	}

	@Override
	public String getName() {
		if(is.hasItemMeta()){
			ItemMeta im = is.getItemMeta();
			return im.getDisplayName();
		}
		return is.getType().toString();
	}

	@Override
	public String getDescription() {
		if(is.hasItemMeta()){
			ItemMeta im = is.getItemMeta();
			if(im.hasLore()){
				return im.getLore().get(0);
			}
		}
		return "";
	}

	@Override
	public ItemStack getItemStack() {
		// TODO Auto-generated method stub
		return is;
	}
}
