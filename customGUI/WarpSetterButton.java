package myvcrime.customGUI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import myvcrime.SpielerProfil;

public class WarpSetterButton extends InventoryButton {

	private String uniqueName; // must be unique for the warps!
	
	public WarpSetterButton(ItemStack is,String uniqueName) {
		super(is);
		this.uniqueName = uniqueName;
	}
	
	@Override
	public void click(Player p){
		SpielerProfil.setWarp(p, uniqueName, p.getLocation());
	}
	
	protected String getUniqueWarpName(){
		return uniqueName;
	}
}
