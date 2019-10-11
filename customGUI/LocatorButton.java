package myvcrime.customGUI;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import myvcrime.chat.Msg;
import net.md_5.bungee.api.ChatColor;

public class LocatorButton extends InventoryButton {

	private Location loc;
	
	public LocatorButton(ItemStack is,Location loc) {
		super(is);
		this.loc = loc;
	}
	
	@Override
	public void click(Player p){
		Msg.INFO.out(p, "Compass aligned!");
		if(loc != null) p.setCompassTarget(loc);
	}

}
