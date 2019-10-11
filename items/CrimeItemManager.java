package myvcrime.items;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrimeItemManager implements Listener {	

	public static CrimeItem getCrimeItem(ItemStack item){
		
		for(Material mat : ToolType.SPADE.mat){
			if(mat == item.getType())
				return new Lockpick(item);
		}
		
		return null;
	}
	
	
	private enum ToolType {
		SPADE(new Material[]{Material.WOOD_SPADE,Material.STONE_SPADE,Material.IRON_SPADE,Material.GOLD_SPADE,Material.DIAMOND_SPADE});
		
		Material[] mat;
		
		ToolType(Material[] mat){
			this.mat = mat;
		}
	}
}
