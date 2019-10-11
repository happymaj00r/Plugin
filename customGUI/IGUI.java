package myvcrime.customGUI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.PlayerInventory;

public interface IGUI {
	public void onClick(Player p, ItemStack clickedItem);
	public void addButton(IButton button);
	public void addButton(IButton button, int index);
	public Inventory getInventory();
	public void addBackPage(IGUI inv);
	void addPage(IGUI inv);
	IGUI getNextPage();
	void open(Player p);
}
