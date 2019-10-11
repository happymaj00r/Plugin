package myvcrime.customGUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IButton {
	public void click(Player p);
	public Material getMaterial();
	public String getName();
	public String getDescription();
	public ItemStack getItemStack();
}
